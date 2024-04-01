package repository;

import model.FileIn;


import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Repository
@Transactional
@ComponentScan("configuration")
public class FileRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public List<FileIn> getFiles() {
        Session session = null;
        Transaction transaction = null;
        List<FileIn> files = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            // Запрос на получение всех файлов
            Query<FileIn> query = session.createQuery("FROM FileIn", FileIn.class);
            files = query.list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(); // Лучше использовать логгер в реальных приложениях
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return files;
    }

    public void saveFile(FileIn fileIn) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction(); // Начало транзакции
            session.save(fileIn);
            transaction.commit(); // Фиксация изменений
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Откат в случае ошибки
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close(); // Закрытие сессии в блоке finally для гарантии закрытия
            }
        }
    }
    public FileIn getFileById(String id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        Long documentId = Long.parseLong(id);
        FileIn fileIn = session.get(FileIn.class, documentId);
        tx.commit();
        session.close();
        return fileIn;
    }
    public void deleteFile(String id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        Long documentId = Long.parseLong(id);
        FileIn fileIn = session.load(FileIn.class, documentId);
        session.delete(fileIn);
        tx.commit();
        session.close();
    }
}
