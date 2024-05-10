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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TopupServiceTest {
    @InjectMocks
    TopupService topupService;

    @Autowired
    TopupRepository topupRepository;

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
    void testGetAllTopups() {
        List<Topup> topups = topupService.getTopups(null);
        assertNotNull(topups);
        assertEquals(topups.size(), 2);
    }

    @Test
    void testGetAllTopupsWithBuyerID() {
        List<Topup> topups = topupService.getTopups("test_buyer1");
        assertNotNull(topups);
        assertEquals(topups.size(), 1);
        Topup topup1 = topups.getFirst();
        assertEquals(topup1.getBuyerId(), "test_buyer1");
        assertEquals(topup1.getAccountNumber(), "4000123456789010");
    }

    @Test
    void testCancelTopup() {
        List<Topup> topups = topupService.getTopups("test_buyer1");
        Topup topup1 = topups.getFirst();
        topup1 = topupService.cancelTopup(topup1.getBuyerId());
        assertEquals(topup1.getApproval(), "CANCELLED");
    }

    @Test
    void testDeleteTopup() {
        List<Topup> topups = topupService.getTopups("test_buyer2");
        Topup topup1 = topups.getFirst();
        topup1 = topupService.deleteTopup(topup1.getBuyerId());

        topups = topupService.getTopups("test_buyer2");
        assertEquals(topups.size(), 0);
    }
}
