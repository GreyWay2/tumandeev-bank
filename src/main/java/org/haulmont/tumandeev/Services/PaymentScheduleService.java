package org.haulmont.tumandeev.Services;

import org.haulmont.tumandeev.Models.PaymentSchedule;
import org.haulmont.tumandeev.Repos.PaymentScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentScheduleService {

    @Autowired
    final PaymentScheduleRepository paymentScheduleRepository;

    public PaymentScheduleService(PaymentScheduleRepository paymentScheduleRepository) {
        this.paymentScheduleRepository = paymentScheduleRepository;
    }

    public void deleteById(Long id) {
        paymentScheduleRepository.delete(id);
    }

    public void save(PaymentSchedule schedule) {
        paymentScheduleRepository.save(schedule);
    }
}
