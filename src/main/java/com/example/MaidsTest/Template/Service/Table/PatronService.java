package com.example.MaidsTest.Template.Service.Table;

import com.example.MaidsTest.Template.Aspect.Loggable;
import com.example.MaidsTest.Template.Exception.PatronNotFoundException;
import com.example.MaidsTest.Template.Model.Table.Patron;
import com.example.MaidsTest.Template.Repository.Table.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatronService {

    @Autowired
    PatronRepository patronRepository;

    @Loggable
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    @Loggable
    @Cacheable("patron")
    public Patron getPatronById(Long id) {
        return patronRepository.findById(id)
                .orElseThrow(() -> new PatronNotFoundException("Patron not found"));
    }

    @Loggable
    public Patron addPatron(Patron book) {
        return patronRepository.save(book);
    }

    @Loggable
    @CacheEvict(value = "patron", key = "#id")
    public Patron updatePatron(Long id, Patron patronDetails) {
        Patron patron = getPatronById(id);

        patron.setName(patronDetails.getName());
        patron.setAddress(patronDetails.getAddress());
        patron.setPhoneNumber(patronDetails.getPhoneNumber());
        return patronRepository.save(patron);
    }

    @Loggable
    public void deletePatron(Long id) {
        Optional<Patron> patron = patronRepository.findById(id);

        if (patron.isEmpty()) {
            throw new PatronNotFoundException("Patron with id " + id + " not found");
        }

        patronRepository.deleteById(id);
    }

}
