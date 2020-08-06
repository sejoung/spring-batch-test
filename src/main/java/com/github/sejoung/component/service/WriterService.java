package com.github.sejoung.component.service;

import com.github.sejoung.component.entity.Writer;
import com.github.sejoung.component.repositories.ReaderRepository;
import com.github.sejoung.component.repositories.WriterRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WriterService {

    private final ReaderRepository readerRepository;

    private final WriterRepository writerRepository;

    public void save(List<? extends Writer> items) {
        items.forEach(this::saveWriter);
    }
    
    private void saveWriter(Writer writer) {
        var reader = readerRepository.findById(writer.getReaderKey()).orElseThrow();
        try {
            writerRepository.save(writer);
            reader.synchronizeSuccess();
        } catch (Exception e) {
            reader.synchronizeFail(e.getMessage());
        }

        readerRepository.save(reader);
    }

}
