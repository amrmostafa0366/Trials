package com.trials.tryAll.Repositories;

import com.trials.tryAll.Models.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends JpaRepository<Guest,Long> {

    Guest findByGuestName(String guestName);
}
