package com.trials.tryAll.Repositories;

import com.trials.tryAll.Models.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill,Long> {

    List<Bill> findByGuestGuestIdOrderByBillDateDesc(long guestId);
    List<Bill> findByGuestGuestIdAndPaymentPayedOrderByBillDateDesc(long guestId, boolean payed);


}
