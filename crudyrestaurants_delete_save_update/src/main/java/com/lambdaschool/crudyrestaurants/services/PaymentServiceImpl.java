package com.lambdaschool.crudyrestaurants.services;


import com.lambdaschool.crudyrestaurants.models.Payment;
import com.lambdaschool.crudyrestaurants.models.Restaurant;
import com.lambdaschool.crudyrestaurants.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "paymentService")
public class PaymentServiceImpl implements PaymentService
{
    @Autowired
    PaymentRepository paymentrepos;

    @Override
    public Payment findPaymentById(long id)
    {
        return paymentrepos.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Payment " + Long.toString(id) + " Not Found"));
    }

    @Override
    public Payment save(Payment payment)
    {
        // restaurants not added through payments
        if (payment.getRestaurants().size() > 0)
        {
            throw new EntityNotFoundException("Restaurants not added through payments");
        }

        Payment newPayment = new Payment();
        newPayment.setRestaurants(new ArrayList<>());
        newPayment.setType(payment.getType());

        return paymentrepos.save(newPayment);
    }
}
