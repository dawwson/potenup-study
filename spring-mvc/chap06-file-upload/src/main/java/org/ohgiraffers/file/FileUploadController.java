package org.ohgiraffers.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class FileUploadController {

    private final ResourceLoader resourceLoader;

    @Autowired
    public FileUploadController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostMapping("single-file")
    public String singleFileUpload(
            @RequestParam MultipartFile singleFile, String singleFileDescription, Model model)
            throws IOException {
        System.out.println("single file : " + singleFile);
        System.out.println("single file description: " + singleFileDescription);

        Resource resource = resourceLoader.getResource("classpath:static/img/single");
        String filePath = null;

        // 경로에 파일이 없으면 지정한 경로에 폴더를 만든다.
        if (!resource.exists()) {
            String root = "src/main/resources/static/img/single";
            File file = new File(root);
            file.mkdirs();
        } else {
            filePath =
                    resourceLoader.getResource("classpath:static/img/single").getFile().getAbsolutePath();
        }

        String originFileName = singleFile.getOriginalFilename();
        String ext = originFileName.substring(originFileName.lastIndexOf("."));
        String savedName = UUID.randomUUID().toString().replace("-", "") + ext;

        try {
            // 바이너리 데이터 -> File 객체
            singleFile.transferTo(new File(filePath + "/" + savedName));
            model.addAttribute("message", "파일 업로드 성공");
            model.addAttribute("img", "static/img/single/" + savedName);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", e.getMessage());
        }
        return "result";
    }

    @PostMapping("multi-file")
    public String multiFileUpload(
            @RequestParam MultipartFile[] multiFiles, String multiFileDescription, Model model)
            throws IOException {
        System.out.println("multi file : " + multiFiles);
        System.out.println("multi file description: " + multiFileDescription);

        Resource resource = resourceLoader.getResource("classpath:static/img/multi");
        String filePath = null;

        if (!resource.exists()) {
            String root = "src/main/resources/static/img/multi";
            File file = new File(root);
            file.mkdirs();
        } else {
            filePath =
                    resourceLoader.getResource("classpath:static/img/multi").getFile().getAbsolutePath();
        }

        System.out.println("multi : " + filePath);

        List<FileDTO> files = new ArrayList<>();
        List<String> saveFiles = new ArrayList<>();

        try {
            for (MultipartFile multiFile : multiFiles) {
                String originFileName = multiFile.getOriginalFilename();
                String ext = originFileName.substring(originFileName.lastIndexOf("."));
                String savedName = UUID.randomUUID().toString().replace("-", "") + ext;

                files.add(new FileDTO(originFileName, savedName, filePath, ext));
                multiFile.transferTo(new File(filePath + "/" + savedName));

                saveFiles.add("static/img/multi/" + savedName);
            }

            model.addAttribute("message", "파일 업로드 성공!");
            model.addAttribute("imgs", saveFiles);
        } catch (Exception e) {
            e.printStackTrace();

            for (FileDTO fileDTO : files) {
                new File(filePath + "/" + fileDTO.getSavedFileName()).delete();
            }

            model.addAttribute("message", "파일 업로드 실패!");
        }
        return "result";
    }
}
