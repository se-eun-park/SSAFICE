package com.jetty.ssafficebe.schedule.repository;

import com.jetty.ssafficebe.schedule.entity.Remind;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RemindRepository extends JpaRepository<Remind, Long> {

}
