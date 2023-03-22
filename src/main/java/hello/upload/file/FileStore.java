package hello.upload.file;

import hello.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename){
        return fileDir+filename;
    }

    public List<UploadFile>storeFiles(List<MultipartFile>multipartFiles) throws IOException {
        List<UploadFile>storeFileResult=new ArrayList<>();
        for(MultipartFile multipartFile:multipartFiles){
            if(!multipartFile.isEmpty()){
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    public UploadFile storeFile(MultipartFile multipartFile){
        if(multipartFile.isEmpty()){
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String changedFileName = UUID.randomUUID().toString();
        String extract=typeExtract(originalFilename);
        
        String storeFileName=changedFileName+"."+extract;

        return new UploadFile(originalFilename,storeFileName);
    }
    
    private String typeExtract(String originalFilename){
        int pos=originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos+1);
    }
}
