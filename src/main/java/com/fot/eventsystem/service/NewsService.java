package com.fot.eventsystem.service;

import com.fot.eventsystem.model.News;
import com.fot.eventsystem.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private FileService fileService;

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    public void saveNews(String title, String description, MultipartFile imageFile) throws IOException {
        News news = new News();
        news.setTitle(title);
        news.setDescription(description);

        String fileName = fileService.saveFile(imageFile);
        news.setImage(fileName);

        newsRepository.save(news);
    }

    public void deleteNews(Integer id) {
        News news = newsRepository.findById(id).orElse(null);
        if (news != null) {
            fileService.deleteFile(news.getImage());
            newsRepository.delete(news);
        }
    }
}
