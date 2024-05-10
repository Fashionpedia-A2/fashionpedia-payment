package id.ac.ui.cs.advprog.fashionpediapayment.service;


import id.ac.ui.cs.advprog.fashionpediapayment.model.Topup;
import id.ac.ui.cs.advprog.fashionpediapayment.repository.TopupRepository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TopupServiceTest {
    @InjectMocks
    TopupService topupService;

    @Autowired
    TopupRepository topupRepository;

    static Timestamp afterFirst;
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");

    @BeforeAll
    static void setUp() {}

    @Test
    void testCreateTopupWithoutPhoto () {
        Topup topup =  topupService.createTopup(
        "test_buyer1", "Transfer dengan Kartu Kredit",
        "Visa", "4000123456789010", 100000,null);

        assertNotNull(topup);
        Topup repoTopup = topupRepository.getReferenceById(topup.getTopupId());
        assertNotNull(repoTopup);

        assertEquals(repoTopup.getBuyerId(), topup.getBuyerId());
        assertEquals(repoTopup.getAccountNumber(), topup.getAccountNumber());
        assertEquals(repoTopup.getBankName(), topup.getBankName());
        assertEquals(repoTopup.getMethod(), topup.getMethod());

        afterFirst = new Timestamp(System.currentTimeMillis());
    }

    @Test
    void testCreateTopupWithPhoto () {
        Topup topup =  topupService.createTopup(
                "test_buyer2", "Transfer dengan Kartu Kredit",
                "Mastercard", "5425233430109903", 100000,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ98fKfQIFcbZ9RWTH07lvObeg1poKM-RLaJCqNqm_BCg&s");

        assertNotNull(topup);
        Topup repoTopup = topupRepository.getReferenceById(topup.getTopupId());
        assertNotNull(repoTopup);

        assertEquals(repoTopup.getBuyerId(), topup.getBuyerId());
        assertEquals(repoTopup.getAccountNumber(), topup.getAccountNumber());
        assertEquals(repoTopup.getBankName(), topup.getBankName());
        assertEquals(repoTopup.getMethod(), topup.getMethod());
        assertEquals(repoTopup.getPhotoProof(), topup.getPhotoProof());
    }


    @Test
    void testGetAllTopups() throws ExecutionException, InterruptedException {
        List<Topup> topups = topupService.getTopups(null, null, null).get();
        assertNotNull(topups);
        assertEquals(topups.size(), 2);
    }

    @Test
    void testGetAllTopupsWithBuyerID() throws ExecutionException, InterruptedException {
        List<Topup> topups = topupService.getTopups("test_buyer1", null, null).get();
        assertNotNull(topups);
        assertEquals(topups.size(), 1);
        Topup topup1 = topups.getFirst();
        assertEquals(topup1.getBuyerId(), "test_buyer1");
        assertEquals(topup1.getAccountNumber(), "4000123456789010");
    }

    @Test
    void testCancelTopup() throws ExecutionException, InterruptedException {
        List<Topup> topups = topupService.getTopups("test_buyer1", null, null).get();
        Topup topup1 = topups.getFirst();
        topup1 = topupService.cancelTopup(topup1.getBuyerId());
        assertEquals(topup1.getApproval(), "CANCELLED");
    }

    @Test
    void testFilterByApproval() throws ExecutionException, InterruptedException{
        List<Topup> topups = topupService.getTopups(null, "PENDING", null).get();
        assertNotNull(topups);
        assertEquals(topups.size(), 1);
        Topup topup1 = topups.getFirst();
        topup1 = topupService.cancelTopup(topup1.getBuyerId());
        assertEquals(topup1.getApproval(), "PENDING");
    }

    @Test
    void testFilterByDate() throws ExecutionException, InterruptedException {
        String date = afterFirst.toLocalDateTime().format(formatter);
        List<Topup> topups = topupService.getTopups(null, null, date).get();

        assertNotNull(topups);
        assertEquals(topups.size(), 1);
        Topup topup1 = topups.getFirst();
        topup1 = topupService.cancelTopup(topup1.getBuyerId());
        assertEquals(topup1.getBuyerId(), "test_buyer2");
    }

    @Test
    void testDeleteTopup() throws ExecutionException, InterruptedException {
        List<Topup> topups = topupService.getTopups("test_buyer2", null, null).get();
        Topup topup1 = topups.getFirst();
        topupService.deleteTopup(topup1.getBuyerId());

        topups = topupService.getTopups("test_buyer2", null, null).get();
        assertEquals(topups.size(), 0);
    }
}
