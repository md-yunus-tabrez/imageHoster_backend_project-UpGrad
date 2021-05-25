package com.upgrad.technical.service.business;


import com.upgrad.technical.service.dao.ImageDao;
import com.upgrad.technical.service.dao.UserDao;
import com.upgrad.technical.service.entity.ImageEntity;
import com.upgrad.technical.service.entity.UserAuthTokenEntity;
import com.upgrad.technical.service.exception.ImageNotFoundException;
import com.upgrad.technical.service.exception.UnauthorizedException;
import com.upgrad.technical.service.exception.UserNotSignedInException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

@Service
public class AdminService {

    @Autowired
    private ImageDao imageDao;
    // get image from database
    public ImageEntity getImage(final String imageUuid, final String authorization) throws ImageNotFoundException, UnauthorizedException, UserNotSignedInException {

    //  fetching user with given access token
        UserAuthTokenEntity userAuthTokenEntity = imageDao.getUserAuthToken(authorization);
    //  handling not signed in exception
        if(userAuthTokenEntity == null) {
            throw new UserNotSignedInException("USR-001", "You are not Signed in, sign in first to get the details of the image.");
        }

    //  fetching user role
        String role = userAuthTokenEntity.getUser().getRole();
    //  handling unauthorized exception
        if(!role.equals("admin")) {
            throw new UnauthorizedException("ATH-001", "UNAUTHORIZED Access, Entered user is not an admin.");
        }

    //  fetching image details from database
        ImageEntity image = imageDao.getImage(imageUuid);
    //  exception handling for image id not found
        if(image == null) {
            throw new ImageNotFoundException("IMG-001", "Image with Uuid not found");
        }
    //  Returning image details
        return image;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ImageEntity updateImage(final ImageEntity imageEntity, final String authorization) throws ImageNotFoundException, UnauthorizedException, UserNotSignedInException {

    //  fetchiing user with given access token
        UserAuthTokenEntity userAuthTokenEntity = imageDao.getUserAuthToken(authorization);
    //  handling user not signed exception
        if(userAuthTokenEntity == null) {
            throw new UserNotSignedInException("USR-001", "You are not Signed in, sign in first to get the details of the image");
        }

    //  fetching user role
        String role = userAuthTokenEntity.getUser().getRole();
    //  handling unauthorized exception
        if(!role.equals("admin")) {
            throw new UnauthorizedException("ATH-001", "UNAUTHORIZED Access, Entered user is not an admin.");
        }

    //  fetching image to be updated
        ImageEntity updationImage = imageDao.getImageById(imageEntity.getId());
    //  handling image not found exception
        if(updationImage == null) {
            throw new ImageNotFoundException("IMG-001", "Image with Id not found");
        }

    //  updating image
        updationImage.setImage(imageEntity.getImage());
        updationImage.setStatus(imageEntity.getStatus());
        updationImage.setDescription(imageEntity.getDescription());
        updationImage.setName(imageEntity.getName());

        imageDao.updateImage(updationImage); // update the image in database
        return updationImage;
    }
}
