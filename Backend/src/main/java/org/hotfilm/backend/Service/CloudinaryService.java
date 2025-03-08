package org.hotfilm.backend.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadFile(MultipartFile file) {
        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Error uploading file to Cloudinary: " + e.getMessage(), e);
        }
    }

    public String deleteFile(String imageUrl) {
        String publicId = extractPublicIdFromUrl(imageUrl);
        try {
            Map<?, ?> result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            String resultStatus = result.get("result").toString();

            if ("ok".equals(resultStatus)) {
                return "File deleted successfully";
            } else {
                return "Failed to delete file: " + resultStatus;
            }
        } catch (IOException e) {
            throw new RuntimeException("Error deleting file from Cloudinary: " + e.getMessage(), e);
        }
    }


    public String extractPublicIdFromUrl(String url) {
        String[] parts = url.split("/");
        String fileName = parts[parts.length - 1];
        return fileName.split("\\.")[0];
    }
}
