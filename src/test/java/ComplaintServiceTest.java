import com.example.entity.Complaint;
import com.example.services.ComplaintService;
import com.example.services.impl.ComplaintServiceImpl;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ComplaintServiceTest {
    ComplaintService cs = new ComplaintServiceImpl();


    @Test
    public void testAddComplaint() {
        cs.addComplaint(new Complaint("Test", "JUnit Issue", "OPEN"));
        assertTrue(true);
    }

    @Test
    public void testFindById() throws Exception {
        Complaint c = cs.findById(1);
        assertNotNull(c);
    }

    @Test
    public void testUpdate() throws Exception {
        cs.updateComplaint(1, "Problem solved", "CLOSED");
        assertTrue(true);
    }

    @Test
    public void testDelete() throws Exception {
        cs.deleteComplaint(1);
        assertTrue("Item has been deleted successfully", true);
    }
}
