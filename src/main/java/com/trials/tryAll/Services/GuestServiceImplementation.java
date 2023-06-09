package com.trials.tryAll.Services;
import com.trials.tryAll.Errors.ConflictException;
import com.trials.tryAll.Errors.NotFoundException;
import com.trials.tryAll.Models.*;
import com.trials.tryAll.Repositories.BillRepository;
import com.trials.tryAll.Repositories.GuestRepository;
import com.trials.tryAll.Repositories.ReservationRepository;
import com.trials.tryAll.Repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class GuestServiceImplementation implements GuestService{

    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private BillRepository billRepository;

    @Override
    public Guest saveGuest(Guest guest) {
          if(guestRepository.findByGuestName(guest.getGuestName())!=null){
              throw new ConflictException("This Guest Is Already Exist");
          }
            return guestRepository.save(guest);
    }

    @Override
    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    @Override
    public Guest getGuestById(long id) {
        try{
            return guestRepository.findById(id).get();
        }catch (NoSuchElementException e){
            throw new NotFoundException("No Guest With That ID");
        }
    }

    @Override
    public void deleteGuestById(long id) {
        if(guestRepository.existsById(id)){
            guestRepository.deleteById(id);
        }else{
            throw new NotFoundException("No Guest With That ID");
        }

    }

    @Override
    public Guest updateGuest(long id, Guest guest) {
        Guest guestDB;
        try {
            guestDB = guestRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new NotFoundException("No Guest With That ID");
        }
        if (guestRepository.findByGuestName(guest.getGuestName()) != null) {
            throw new ConflictException("This Guest Name Is Already Used");
        }
        guestDB.setGuestName(guest.getGuestName());
        return guestRepository.save(guestDB);
    }

    @Override
    public Guest checkIn(long guestId, long roomId, CheckInCheckOutDates dates) {

        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new NotFoundException("No Guest With That ID"));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("No Room With That ID"));

        if(room.getGuest() != null){
            throw new ConflictException("Room Is Not Available");
        }else if(guest.getRoom() != null){
            throw new ConflictException("Guest Is Already CheckedIn A Room");
        }
        List<Reservation> existingReservations = reservationRepository.findBetweenDates(roomId, dates.getCheckInDate(), dates.getCheckOutDate());
        if(!existingReservations.isEmpty()){
                throw new ConflictException("There Are Existing Reservations Between The Specified Dates");
            }

        long numberOfDays = ChronoUnit.DAYS.between(
                dates.getCheckInDate().toInstant(), dates.getCheckOutDate().toInstant()
        );
        double cost = room.getNightCost()*numberOfDays;

        Bill bill = new Bill(cost,
                "Accommodation In Room "+room.getRoomNumber()+" For: "+numberOfDays+" Days From "
                        + dates.getCheckInDate() + " To "+dates.getCheckOutDate(), guest);

        room.setCheckInDate(dates.getCheckInDate());
        room.setCheckOutDate(dates.getCheckOutDate());

        guest.setRoom(room);
        room.setGuest(guest);
        roomRepository.save(room);
        billRepository.save(bill);
        return guestRepository.save(guest);
    }

    @Override
    public Guest checkOut(long guestId) {
        Guest guest = guestRepository.findById(guestId).orElseThrow(()-> new NotFoundException("Guest Not Found"));
        if(guest.getRoom()!=null){
            List<Bill> existingUnPayedBills = billRepository.findByGuestGuestIdAndPaymentPayedOrderByBillDateDesc(guestId,false);
            if(!existingUnPayedBills.isEmpty()) {
                throw new ConflictException("There Are Un-payed Bills For That Guest");
            }else{
                Room room = roomRepository.findById(guest.getRoom().getRoomId()).get();
                guest.setRoom(null);
                room.setGuest(null);
                room.setCheckInDate(null);
                room.setCheckOutDate(null);
                roomRepository.save(room);
                return guestRepository.save(guest);
            }
        }
        throw new NotFoundException("Guest Has No Room");
    }

    @Override
    public Reservation addReservation(long guestId, long roomId,CheckInCheckOutDates dates) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new NotFoundException("Guest not found"));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("Room not found"));


        // Check if there are any existing reservations between the given dates
        List<Reservation> existingReservations = reservationRepository.findBetweenDates(roomId, dates.getCheckInDate(), dates.getCheckOutDate());
        if (!existingReservations.isEmpty()) {
            throw new ConflictException("There are existing reservations between the specified dates");
        }

        Reservation reservation = new Reservation(guest,room,dates.getCheckInDate(),dates.getCheckOutDate());

        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservationById(long guestId, long reservationId) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new NotFoundException("Guest not found"));
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        if (reservation.getGuest().getGuestId() != guest.getGuestId()) {
            throw new ConflictException("Guest is not authorized to delete this reservation");
        }

        Room room = reservation.getRoom();
        guest.getReservations().remove(reservation);
        room.getReservations().remove(reservation);

        reservationRepository.deleteById(reservationId);
    }

    @Override
    public List<Bill> getAllBillsByGuestId(long guestId) {
        try{
            return billRepository.findByGuestGuestIdOrderByBillDateDesc(guestId);
        }catch (Exception e){
            throw new NotFoundException("Fuck");
        }
    }

    @Override
    public List<Bill> getAllPayedBillsByGuestId(long guestId) {
        try{
            return billRepository.findByGuestGuestIdAndPaymentPayedOrderByBillDateDesc(guestId,true);
        }catch (Exception e){
            throw new NotFoundException("Fuck Your Payed Bills");
        }
    }

    @Override
    public List<Bill> getAllUnPayedBillsByGuestId(long guestId) {
        try{
            return billRepository.findByGuestGuestIdAndPaymentPayedOrderByBillDateDesc(guestId,false);
        }catch (Exception e){
            throw new NotFoundException("Fuck Your UnPayed Bills");
        }
    }

    @Override
    public Bill payBill(long guestId, long billId, Payment payment) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new NotFoundException("Guest not found"));
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new NotFoundException("Bill not found"));

        if (bill.getGuest().getGuestId() != guest.getGuestId()) {
            throw new ConflictException("Guest is not authorized to Pay this Bill");
        }
        payment.setPayed(true);
        payment.setPaymentDate(new Date());
        bill.setPayment(payment);

        return billRepository.save(bill);
    }

    @Override
    public Bill makeOrder(long guestId, Order order) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new NotFoundException("Guest not found"));

        Bill bill = new Bill(order.getOrderCost(),order.getOrderDescription(),guest);

        return billRepository.save(bill);
    }


    //CRUD


}
