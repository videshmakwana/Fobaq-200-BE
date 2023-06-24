package com.brilworks.accounts.services;

import org.springframework.transaction.annotation.Transactional;

public interface ImageDownloadUtilService {
    @Transactional
    String downloadImage(Long postId, String imageUrl);
}
