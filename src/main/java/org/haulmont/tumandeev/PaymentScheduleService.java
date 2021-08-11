package org.haulmont.tumandeev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentScheduleService {

    @Autowired
    PaymentScheduleRepository paymentScheduleRepository;

    public PaymentScheduleService() {
    }

    public void deleteById(Long id) {
        paymentScheduleRepository.delete(id);
    }

    public void delete(PaymentSchedule schedule) {
        paymentScheduleRepository.delete(schedule);
    }

    public List<PaymentSchedule> findAll() {
        return paymentScheduleRepository.findAll();
    }

    public void save(PaymentSchedule schedule) {
        paymentScheduleRepository.save(schedule);
    }
}
