package id.ac.ui.cs.advprog.fashionpediapayment.service;


import id.ac.ui.cs.advprog.fashionpediapayment.model.Topup;
import id.ac.ui.cs.advprog.fashionpediapayment.repository.TopupRepository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
@Transactional
public class TopupServiceTest {
    @InjectMocks
    @Autowired
    TopupService topupService;

    @Autowired
    TopupRepository topupRepository;

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    static Topup topup1, topup2;
    static Topup repoTopup1, repoTopup2;

    @BeforeAll
    static void setUp() {
        topup1 = new Topup("test_buyer1", "Transfer dengan Kartu Kredit",
                "Visa", "4000123456789010", 100000);
        topup2 = new Topup("test_buyer2", "Transfer dengan Kartu Kredit",
                "Mastercard", "5425233430109903", 100000,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ98fKfQIFcbZ9RWTH07lvObeg1poKM-RLaJCqNqm_BCg&s");
    }
    @BeforeEach
    void beforeEach() {
        repoTopup1 = topupRepository.save(topup1);
        repoTopup2 = topupRepository.save(topup2);
    }

    @Test
    void testCreateTopupWithoutPhoto () {
        Topup topup =  topupService.createTopup(
        "test_buyer3", "Transfer dengan QRIS",
        "Gopay", "081234567890", 20000,null);

        assertNotNull(topup);
        Topup repoTopup = topupRepository.getReferenceById(topup.getTopupId());
        assertNotNull(repoTopup);

        assertEquals(repoTopup.getBuyerId(), topup.getBuyerId());
        assertEquals(repoTopup.getAccountNumber(), topup.getAccountNumber());
        assertEquals(repoTopup.getBankName(), topup.getBankName());
        assertEquals(repoTopup.getMethod(), topup.getMethod());
    }

    @Test
    void testCreateTopupWithPhoto () {
        Topup topup =  topupService.createTopup(
        "test_buyer4", "Transfer dengan Kartu Kredit",
        "Mastercard", "41242852932021", 120000,
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
    void testGetAllTopups()  {
        List<Topup> topups = topupService.getTopups(null, null, null);
        System.out.println(topups);
        assertNotNull(topups);
        assertEquals(2, topups.size());
    }

    @Test
    void testGetAllTopupsWithBuyerID()  {
        List<Topup> topups = topupService.getTopups("test_buyer1", null, null);
        assertNotNull(topups);
        assertEquals(topups.size(), 1);
        Topup topup1 = topups.getFirst();
        assertEquals("test_buyer1", topup1.getBuyerId());
        assertEquals("4000123456789010", topup1.getAccountNumber());
    }

    @Test
    void testCancelTopup()  {
        List<Topup> topups = topupService.getTopups("test_buyer1", null, null);
        Topup topup1 = topups.getFirst();
        topup1 = topupService.cancelTopup(topup1.getTopupId());
        assertEquals("CANCELLED", topup1.getApproval());
    }

    @Test
    void testFilterByApproval() {
        Topup test_topup = topupService.cancelTopup(topup2.getTopupId());
        System.out.println(test_topup);

        List<Topup> topups = topupService.getTopups(null, "PENDING", null);
        assertNotNull(topups);
        assertEquals(1, topups.size());
        Topup topup1 = topups.getFirst();
        assertEquals("PENDING", topup1.getApproval());
    }

    @Test
    void testFilterByDate()  {
        Topup topup =  topupService.createTopup(
                "test_buyer3", "Transfer dengan QRIS",
                "Gopay", "081234567890", 20000,null);

        System.out.println(topup.getTopupId());
        topup = topupRepository.save(topup);
        Timestamp timestamp = topup.getDate();
        System.out.println(topup.getDate());

        timestamp.setTime(timestamp.getTime()-1000);
        String date = timestamp.toLocalDateTime().format(formatter);
        System.out.println(date);
        List<Topup> topups = topupService.getTopups(null, null, date);

        assertNotNull(topups);
        System.out.println(topups);
        assertFalse(topups.isEmpty());
        assertTrue(topups.contains(topup));
//        assertEquals(topup1.getBuyerId(), "test_buyer3");
    }

    @Test
    void testDeleteTopup()  {
        topupService.deleteTopup(repoTopup2.getTopupId());

        List<Topup> topups = topupService.getTopups("test_buyer2", null, null);
        assertEquals(0, topups.size());
    }
}
