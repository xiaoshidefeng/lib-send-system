package com.example.demo.repository;

import com.example.demo.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by cw on 2017/9/30.
 */
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    List<Subscription> findBySended(Boolean sended);

    List<Subscription> findByEmailAndAndUrlAndAndSended(String email, String url, Boolean sended);
}
