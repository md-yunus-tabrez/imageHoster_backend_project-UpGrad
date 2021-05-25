package com.upgrad.technical.service.business;

import com.upgrad.technical.service.dao.ImageDao;
import com.upgrad.technical.service.dao.UserDao;
import com.upgrad.technical.service.entity.ImageEntity;
import com.upgrad.technical.service.entity.UserAuthTokenEntity;
import com.upgrad.technical.service.entity.UserEntity;
import com.upgrad.technical.service.exception.UploadFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

@Service
public class ImageUploadService {

    @Autowired
    private ImageDao imageDao;

    // Upload an image in base 64 encoded format
    @Transactional(propagation = Propagation.REQUIRED)
    public ImageEntity upload(ImageEntity imageEntity, final String authorizationToken) throws UploadFailedException {
    //  fetching user with given access token
        UserAuthTokenEntity userAuthTokenEntity = imageDao.getUserAuthToken(authorizationToken);
    //  throwing exception when user is not logged in
        if(userAuthTokenEntity == null) {
            throw new UploadFailedException("UP-001", "User is not Signed in, sign in to upload an image.");
        }
        imageEntity.setUser_id(userAuthTokenEntity.getUser());
        imageDao.createImage(imageEntity); //   saving image in database
        return imageEntity;
    }
}
