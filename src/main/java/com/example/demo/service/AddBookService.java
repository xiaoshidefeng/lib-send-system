package com.example.demo.service;

import com.example.demo.domain.Subscription;
import com.example.demo.repository.SubscriptionRepository;
import com.example.demo.util.IsNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by cw on 2017/10/1.
 */
@Service
public class AddBookService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Boolean addBook(Subscription sub) {
        String email = sub.getEmail();
        String url = sub.getUrl();
        if (!IsNull.isNullField(email, url)) {
            if (!email.contains("@") || !email.contains(".")) {
                return false;
            }

            if (!url.contains("http://my.lib.lsu.edu.cn/opac/item.php?marc_no=")) {
                return false;
            }

            List<Subscription> list = subscriptionRepository.findByEmailAndAndUrlAndAndSended(email, url, false);

            if (list == null || list.size() == 0) {
                // 接收到请求，记录请求内容
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = attributes.getRequest();
                sub.setIp(request.getRemoteAddr());
                sub.setSended(false);

                subscriptionRepository.save(sub);
                return true;
            }

        }
        return false;

    }

}
