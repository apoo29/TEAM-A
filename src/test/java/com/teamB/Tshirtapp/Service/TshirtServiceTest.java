@SpringBootTest
@RunWith(SpringRunner.class)
public class TshirtServiceTest {

    @Autowired
    private TshirtService tshirtService;

    @MockBean
    private TshirtOrderRepository tshirtOrderRepository;

    private TshirtOrder validOrder;

    @Before
    public void setUp() {
        validOrder = new TshirtOrder();
        validOrder.setSize("M");
        validOrder.setEmail("test@example.com");
        validOrder.setName("John Doe");
        validOrder.setAddress1("123 Street");
        validOrder.setCity("City");
        validOrder.setStateOrProvince("State");
        validOrder.setPostalCode("123456");
        validOrder.setCountry("Country");
    }

    @Test
    public void testOrderTshirtSuccess() {
        Mockito.when(tshirtOrderRepository.save(Mockito.any(TshirtOrder.class))).thenReturn(validOrder);

        TshirtOrder savedOrder = tshirtService.orderTshirt(validOrder);

        assertNotNull(savedOrder);
        assertEquals("Processing", savedOrder.getStatus());
        assertEquals("test@example.com", savedOrder.getEmail());
    }

    @Test
    public void testOrderTshirtInvalidEmail() {
        validOrder.setEmail("invalid-email");

        ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class, () -> tshirtService.orderTshirt(validOrder)
        );

        assertTrue(exception.getMessage().contains("Invalid email address"));
    }

    @Test
    public void testOrderTshirtMissingRequiredFields() {
        validOrder.setName(null);

        ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class, () -> tshirtService.orderTshirt(validOrder)
        );

        assertTrue(exception.getMessage().contains("must not be null"));
    }

    @Test
    public void testOrderTshirtWithFallback() {
        Mockito.when(tshirtOrderRepository.save(Mockito.any(TshirtOrder.class))).thenThrow(new RuntimeException("Database error"));

        TshirtOrder savedOrder = tshirtService.defaultOrderTshirt(validOrder);

        assertNotNull(savedOrder);
        assertEquals("Failed", savedOrder.getStatus());
    }

    @Test
    public void testOrderTshirtHighVolumeOrders() {
        List<TshirtOrder> orders = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            orders.add(validOrder);
        }

        Mockito.when(tshirtOrderRepository.saveAll(Mockito.anyList())).thenReturn(orders);

        List<TshirtOrder> savedOrders = tshirtService.orderTshirtInBulk(orders);

        assertEquals(100, savedOrders.size());
    }
}
