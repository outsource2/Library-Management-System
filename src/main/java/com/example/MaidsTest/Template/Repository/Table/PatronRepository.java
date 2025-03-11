package com.example.MaidsTest.Template.Repository.Table;

import com.example.MaidsTest.Template.Model.Table.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatronRepository extends JpaRepository<Patron, Long> {
}
