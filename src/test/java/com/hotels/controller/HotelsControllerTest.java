package com.hotels.controller;

import com.hotels.model.HotelsModel;
import com.hotels.repo.HotelsRepo;
import com.google.common.collect.Lists;
import com.hotels.service.HotelsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by narubordeesarnsuwan on 4/24/2016 AD.
 */
public class HotelsControllerTest {
    MockMvc mvc;

    @InjectMocks
    HotelsController controller;

    @Mock
    HotelsService hotelsService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldCallGetAllOnetimeAndReturnArrayWithSizeOne() throws Exception {
        List<HotelsModel> results = Lists.<HotelsModel>newArrayList();
        HotelsModel hotel1 = new HotelsModel();
        hotel1.setCity("Bangkok");
        hotel1.setHotelId(99);
        hotel1.setPrice(new Double("192.00"));
        hotel1.setRoom("Deluxe");

        HotelsModel hotel2 = new HotelsModel();
        hotel1.setCity("Japan");
        hotel1.setHotelId(100);
        hotel1.setPrice(new Double("380.00"));
        hotel1.setRoom("Single Bed");

        results.add(hotel1);
        results.add(hotel2);

        when(hotelsService.getAll(anyString(),anyString(),anyString())).thenReturn(results);

        mvc.perform(get("/api/v1/hotels/AAAAA"))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.message", is("success")))
                .andExpect(status().isOk());

        verify(hotelsService, Mockito.times(1)).getAll(anyString(),anyString(),anyString());
        verify(hotelsService, Mockito.times(0)).getHotelsByHotelId(anyInt(),anyString());
    }

    @Test
    public void shouldCallGetByIdOnetimeAndReturnArrayWithSizeOne() throws Exception {
        List<HotelsModel> results = Lists.<HotelsModel>newArrayList();
        HotelsModel hotel1 = new HotelsModel();
        hotel1.setCity("Bangkok");
        hotel1.setHotelId(99);
        hotel1.setPrice(new Double("192.00"));
        hotel1.setRoom("Deluxe");
        results.add(hotel1);


        when(hotelsService.getHotelsByHotelId(anyInt(),anyString())).thenReturn(results);

        mvc.perform(get("/api/v1/hotels/AAAAA?hotel_id=1"))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.message", is("success")))
                .andExpect(status().isOk());

        verify(hotelsService, Mockito.times(1)).getHotelsByHotelId(anyInt(),anyString());
        verify(hotelsService, Mockito.times(0)).getAll(anyString(),anyString(),anyString());
    }
}

//   /* @Test
//    public void shouldReturnVariantsWhenListExistingVariantByProductId() throws Exception {
//        VariantDTO dto = new VariantDTO();
//        dto.setName("test");
//        dto.setNormalPrice(2.00d);
//        dto.setSpecialPrice(1.55d);
//        dto.setQuantity(2);
//        dto.setSku("SKU1");
//
//        when(hotelsRepo.findVariantsPriceByProductId(anyInt())).thenReturn(Lists.newArrayList(dto));
//
//        mvc.perform(get("/api/v1/general/variants?productId=1"))
//                .andExpect(jsonPath("$.data", hasSize(1)))
//                .andExpect(jsonPath("$.data.[0].name", is("test")))
//                .andExpect(jsonPath("$.data.[0].normal_price", is(2.00d)))
//                .andExpect(jsonPath("$.data.[0].special_price", is(1.55d)))
//                .andExpect(jsonPath("$.data.[0].quantity", is(2)))
//                .andExpect(jsonPath("$.data.[0].sku", is("SKU1")))
//                .andExpect(jsonPath("$.message", is("success")))
//                .andExpect(status().isOk());
//
//        verify(hotelsRepo).findVariantsPriceByProductId(anyInt());
//    }
//
//    @Test
//    public void shouldReturnNullWhenGetNonExistingProductByProductKey() throws Exception {
//        when(hotelsRepo.getProductByProductKey(anyString())).thenReturn(null);
//
//        mvc.perform(get("/api/v1/general/products/AB"))
//                .andExpect(jsonPath("$.data", is(nullValue())))
//                .andExpect(jsonPath("$.message", is(GeneralConstant.NOT_FOUND.getContent())))
//                .andExpect(status().is4xxClientError());
//
//        verify(hotelsRepo).getProductByProductKey(anyString());
//    }
//
//
//    @Test
//    public void shouldReturnProductWhenGetExistingProductByProductKey() throws Exception {
//        VariantDTO dto = new VariantDTO();
//        dto.setName("test");
//        dto.setNormalPrice(2.00d);
//        dto.setSpecialPrice(1.55d);
//        dto.setQuantity(2);
//        dto.setSku("SKU1");
//        when(hotelsRepo.getProductByProductKey(anyString())).thenReturn(mockProductModel());
//
//        when(hotelsRepo.findVariantsByProductId(anyInt())).thenReturn(Lists.newArrayList(dto));
//
//        mvc.perform(get("/api/v1/general/products/2166325370469"))
//                .andExpect(jsonPath("$.data.product_id", is(1)))
//                .andExpect(jsonPath("$.data.product_name", is("Olay")))
//                .andExpect(jsonPath("$.data.product_key", is("2166325370469")))
//                .andExpect(jsonPath("$.data.variants[0].sku", is("SKU1")))
//                .andExpect(jsonPath("$.message", is("success")))
//                .andExpect(status().isOk());
//
//        verify(hotelsRepo).findVariantsByProductId(anyInt());
//    }
//
//    @Test
//    public void shouldReturnEmptyArrayWhenListNonExistingVariantByVariantId() throws Exception {
//        when(hotelsRepo.findVariantsByVariantId(anyString())).thenReturn(Lists.newArrayList());
//
//        mvc.perform(get("/api/v1/general/variants/ABC"))
//                .andExpect(jsonPath("$.data", hasSize(0)))
//                .andExpect(jsonPath("$.message", is("success")))
//                .andExpect(status().isOk());
//
//        verify(hotelsRepo).findVariantsByVariantId(anyString());
//    }
//
//    @Test
//    public void shouldReturnVariantsWhenListExistingVariantByVariantId() throws Exception {
//        VariantDTO dto = new VariantDTO();
//        dto.setName("test");
//        dto.setNormalPrice(2.00d);
//        dto.setSpecialPrice(1.55d);
//        dto.setQuantity(2);
//        dto.setSku("SKU1");
//
//        when(hotelsRepo.findVariantsByVariantId(anyString())).thenReturn(Lists.newArrayList(dto));
//
//        mvc.perform(get("/api/v1/general/variants/APPLE"))
//                .andExpect(jsonPath("$.data", hasSize(1)))
//                .andExpect(jsonPath("$.data.[0].name", is("test")))
//                .andExpect(jsonPath("$.data.[0].normal_price", is(2.00d)))
//                .andExpect(jsonPath("$.data.[0].special_price", is(1.55d)))
//                .andExpect(jsonPath("$.data.[0].quantity", is(2)))
//                .andExpect(jsonPath("$.data.[0].sku", is("SKU1")))
//                .andExpect(jsonPath("$.message", is("success")))
//                .andExpect(status().isOk());
//
//        verify(hotelsRepo).findVariantsByVariantId(anyString());
//    }
//
//    @Test
//    public void shouldReturnEmptyArrayWhenListNonExistingVariantByVariantList() throws Exception {
//        when(hotelsRepo.findVariantsByVariants(anyList())).thenReturn(Lists.newArrayList());
//
//        mvc.perform(get("/api/v1/general/variants?ids=1,2,3"))
//                .andExpect(jsonPath("$.data", hasSize(0)))
//                .andExpect(jsonPath("$.message", is("success")))
//                .andExpect(status().isOk());
//
//        verify(hotelsRepo).findVariantsByVariants(anyList());
//    }
//
//    @Test
//    public void shouldReturnVariantsWhenListExistingVariantByVariantList() throws Exception {
//        VariantDTO dto = new VariantDTO();
//        dto.setName("test");
//        dto.setNormalPrice(2.00d);
//        dto.setSpecialPrice(1.55d);
//        dto.setQuantity(2);
//        dto.setSku("SKU1");
//
//        when(hotelsRepo.findVariantsByVariants(anyList())).thenReturn(Lists.newArrayList(dto));
//
//        mvc.perform(get("/api/v1/general/variants?ids=1,2,3"))
//                .andExpect(jsonPath("$.data", hasSize(1)))
//                .andExpect(jsonPath("$.data.[0].name", is("test")))
//                .andExpect(jsonPath("$.data.[0].normal_price", is(2.00d)))
//                .andExpect(jsonPath("$.data.[0].special_price", is(1.55d)))
//                .andExpect(jsonPath("$.data.[0].quantity", is(2)))
//                .andExpect(jsonPath("$.data.[0].sku", is("SKU1")))
//                .andExpect(jsonPath("$.message", is("success")))
//                .andExpect(status().isOk());
//
//        verify(hotelsRepo).findVariantsByVariants(anyList());
//    }
//
//    @Test
//    public void shouldReturnEmptyArrayAndBadRequestWhenListVariantWithInvalidParameter() throws Exception {
//        mvc.perform(get("/api/v1/general/variants"))
//                .andExpect(jsonPath("$.message", is("fail")))
//                .andExpect(status().isBadRequest());
//
//        verify(hotelsRepo, never()).findVariantsByVariantId(anyString());
//        verify(hotelsRepo, never()).findVariantsByVariantId(anyString());
//        verify(hotelsRepo, never()).findVariantsByVariants(anyList());
//    }
//
//    @Test
//    public void shouldReturnEmptyArrayWhenListNonExistingBrand() throws Exception {
//        when(hotelsRepo.findBrands()).thenReturn(Lists.newArrayList());
//
//        mvc.perform(get("/api/v1/general/brands"))
//                .andExpect(jsonPath("$.data", hasSize(0)))
//                .andExpect(jsonPath("$.message", is("success")))
//                .andExpect(status().isOk());
//
//        verify(hotelsRepo).findBrands();
//    }
//
//    @Test
//    public void shouldReturnBrandsWhenListExistingBrand() throws Exception {
//        BrandDTO dto = new BrandDTO();
//        dto.setId("1");
//        dto.setCode("test");
//        dto.setName("test");
//
//        when(hotelsRepo.findBrands()).thenReturn(Lists.newArrayList(dto));
//
//        mvc.perform(get("/api/v1/general/brands"))
//                .andExpect(jsonPath("$.data", hasSize(1)))
//                .andExpect(jsonPath("$.data.[0].id", is("1")))
//                .andExpect(jsonPath("$.data.[0].code", is("test")))
//                .andExpect(jsonPath("$.data.[0].name", is("test")))
//                .andExpect(jsonPath("$.message", is("success")))
//                .andExpect(status().isOk());
//
//        verify(hotelsRepo).findBrands();
//    }
//
//    @Test
//    public void shouldReturnEmptyArrayWhenListNonExistingShop() throws Exception {
//        when(hotelsRepo.findShops()).thenReturn(Lists.newArrayList());
//
//        mvc.perform(get("/api/v1/general/shops"))
//                .andExpect(jsonPath("$.data", hasSize(0)))
//                .andExpect(jsonPath("$.message", is("success")))
//                .andExpect(status().isOk());
//
//        verify(hotelsRepo).findShops();
//    }
//
//    @Test
//    public void shouldReturnShopsWhenListExistingShop() throws Exception {
//        ShopDTO dto = new ShopDTO();
//        dto.setId("1");
//        dto.setName("test");
//
//        when(hotelsRepo.findShops()).thenReturn(Lists.newArrayList(dto));
//
//        mvc.perform(get("/api/v1/general/shops"))
//                .andExpect(jsonPath("$.data", hasSize(1)))
//                .andExpect(jsonPath("$.data.[0].id", is("1")))
//                .andExpect(jsonPath("$.data.[0].name", is("test")))
//                .andExpect(jsonPath("$.message", is("success")))
//                .andExpect(status().isOk());
//
//        verify(hotelsRepo).findShops();
//    }
//
//    @Test
//    public void shouldReturnEmptyArrayWhenListNonExistingVendor() throws Exception {
//        when(hotelsRepo.findVendors()).thenReturn(Lists.newArrayList());
//
//        mvc.perform(get("/api/v1/general/vendors"))
//                .andExpect(jsonPath("$.data", hasSize(0)))
//                .andExpect(jsonPath("$.message", is("success")))
//                .andExpect(status().isOk());
//
//        verify(hotelsRepo).findVendors();
//    }
//
//    @Test
//    public void shouldReturnVendorsWhenListExistingVendor() throws Exception {
//        VendorDTO dto = new VendorDTO();
//        dto.setId("1");
//        dto.setName("test");
//
//        when(hotelsRepo.findVendors()).thenReturn(Lists.newArrayList(dto));
//
//        mvc.perform(get("/api/v1/general/vendors"))
//                .andExpect(jsonPath("$.data", hasSize(1)))
//                .andExpect(jsonPath("$.data.[0].id", is("1")))
//                .andExpect(jsonPath("$.data.[0].name", is("test")))
//                .andExpect(jsonPath("$.message", is("success")))
//                .andExpect(status().isOk());
//
//        verify(hotelsRepo).findVendors();
//    }
//
//    @Test
//    public void shouldReturnEmptyArrayWhenListNonExistingCollection() throws Exception {
//        when(hotelsRepo.findCollections()).thenReturn(Lists.newArrayList());
//
//        mvc.perform(get("/api/v1/general/collections"))
//                .andExpect(jsonPath("$.data", hasSize(0)))
//                .andExpect(jsonPath("$.message", is("success")))
//                .andExpect(status().isOk());
//
//        verify(hotelsRepo).findCollections();
//    }
//
//    @Test
//    public void shouldReturnCollectionsWhenListExistingCollection() throws Exception {
//        CollectionDTO dto = new CollectionDTO();
//        dto.setId("1");
//        dto.setName("test");
//
//        when(hotelsRepo.findCollections()).thenReturn(Lists.newArrayList(dto));
//
//        mvc.perform(get("/api/v1/general/collections"))
//                .andExpect(jsonPath("$.data", hasSize(1)))
//                .andExpect(jsonPath("$.data.[0].id", is("1")))
//                .andExpect(jsonPath("$.data.[0].name", is("test")))
//                .andExpect(jsonPath("$.message", is("success")))
//                .andExpect(status().isOk());
//
//        verify(hotelsRepo).findCollections();
//    }
//
//    @Test
//    public void shouldReturnEmptyArrayWhenSearchNonExistingProduct() throws Exception {
//        when(hotelsRepo.countProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(0);
//
//        mvc.perform(get("/api/v1/general/products/search"))
//                .andExpect(jsonPath("$.data", hasSize(0)))
//                .andExpect(jsonPath("$.message", is("success")))
//                .andExpect(jsonPath("$.total_elements", is(0)))
//                .andExpect(status().isOk());
//
//        verify(hotelsRepo).countProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt());
//        verify(hotelsRepo, never()).findProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt());
//        verify(hotelsRepo, never()).searchVariantsByProductId(anyInt());
//    }
//
//    @Test
//    public void shouldReturnProductsWhenSearchExistingProductWithoutParam() throws Exception {
//        when(hotelsRepo.countProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(1);
//
//        when(hotelsRepo.findProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt()))
//                .thenReturn(Lists.newArrayList(mockProduct()));
//
//        when(hotelsRepo.searchVariantsByProductId(anyInt())).thenReturn(Lists.newArrayList(mockInventory()));
//
//        mvc.perform(get("/api/v1/general/products/search"))
//                .andExpect(jsonPath("$.data", hasSize(1)))
//                .andExpect(jsonPath("$.data.[0].product_id", is(1)))
//                .andExpect(jsonPath("$.data.[0].product_name", is("Olay")))
//                .andExpect(jsonPath("$.data.[0].product_key", is("2166325370469")))
//                .andExpect(jsonPath("$.data.[0].variants[0].inventory_id", is("OLAAB1111111")))
//                .andExpect(jsonPath("$.message", is("success")))
//                .andExpect(jsonPath("$.total_elements", is(1)))
//                .andExpect(status().isOk());
//
//        verify(hotelsRepo).countProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt());
//        verify(hotelsRepo).findProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt());
//        verify(hotelsRepo).searchVariantsByProductId(anyInt());
//    }
//
//    @Test
//    public void shouldReturnProductsWhenSearchExistingProductByTitle() throws Exception {
//        when(hotelsRepo.countProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(1);
//        when(hotelsRepo.findProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt()))
//                .thenReturn(Lists.newArrayList(mockProduct()));
//        when(hotelsRepo.searchVariantsByProductId(anyInt())).thenReturn(Lists.newArrayList(mockInventory()));
//
//        mvc.perform(get("/api/v1/general/products/search?title=test"))
//                .andExpect(jsonPath("$.data", hasSize(1)))
//                .andExpect(jsonPath("$.data.[0].product_id", is(1)))
//                .andExpect(jsonPath("$.data.[0].product_name", is("Olay")))
//                .andExpect(jsonPath("$.data.[0].product_key", is("2166325370469")))
//                .andExpect(jsonPath("$.data.[0].variants[0].inventory_id", is("OLAAB1111111")))
//                .andExpect(jsonPath("$.message", is("success")))
//                .andExpect(jsonPath("$.total_elements", is(1)))
//                .andExpect(status().isOk());
//
//        verify(hotelsRepo).countProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt());
//        verify(hotelsRepo).findProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt());
//        verify(hotelsRepo).searchVariantsByProductId(anyInt());
//    }
//
//    @Test
//    public void shouldReturnProductsWhenSearchExistingProductByTag() throws Exception {
//        when(hotelsRepo.countProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(1);
//        when(hotelsRepo.findProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt()))
//                .thenReturn(Lists.newArrayList(mockProduct()));
//        when(hotelsRepo.searchVariantsByProductId(anyInt())).thenReturn(Lists.newArrayList(mockInventory()));
//
//        mvc.perform(get("/api/v1/general/products/search?tag=test"))
//                .andExpect(jsonPath("$.data", hasSize(1)))
//                .andExpect(jsonPath("$.data.[0].product_id", is(1)))
//                .andExpect(jsonPath("$.data.[0].product_name", is("Olay")))
//                .andExpect(jsonPath("$.data.[0].product_key", is("2166325370469")))
//                .andExpect(jsonPath("$.data.[0].variants[0].inventory_id", is("OLAAB1111111")))
//                .andExpect(jsonPath("$.message", is("success")))
//                .andExpect(jsonPath("$.total_elements", is(1)))
//                .andExpect(status().isOk());
//
//        verify(hotelsRepo).countProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt());
//        verify(hotelsRepo).findProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt());
//        verify(hotelsRepo).searchVariantsByProductId(anyInt());
//    }
//
//    @Test
//    public void shouldReturnProductsWhenSearchExistingProductByProductLine() throws Exception {
//        when(hotelsRepo.countProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(1);
//        when(hotelsRepo.findProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt()))
//                .thenReturn(Lists.newArrayList(mockProduct()));
//        when(hotelsRepo.searchVariantsByProductId(anyInt())).thenReturn(Lists.newArrayList(mockInventory()));
//
//        mvc.perform(get("/api/v1/general/products/search?pline=test"))
//                .andExpect(jsonPath("$.data", hasSize(1)))
//                .andExpect(jsonPath("$.data.[0].product_id", is(1)))
//                .andExpect(jsonPath("$.data.[0].product_name", is("Olay")))
//                .andExpect(jsonPath("$.data.[0].product_key", is("2166325370469")))
//                .andExpect(jsonPath("$.data.[0].variants[0].inventory_id", is("OLAAB1111111")))
//                .andExpect(jsonPath("$.message", is("success")))
//                .andExpect(jsonPath("$.total_elements", is(1)))
//                .andExpect(status().isOk());
//
//        verify(hotelsRepo).countProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt());
//        verify(hotelsRepo).findProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt());
//        verify(hotelsRepo).searchVariantsByProductId(anyInt());
//    }
//
//    @Test
//    public void shouldReturnProductsWhenSearchExistingProductByBrand() throws Exception {
//        when(hotelsRepo.countProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(1);
//        when(hotelsRepo.findProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt()))
//                .thenReturn(Lists.newArrayList(mockProduct()));
//        when(hotelsRepo.searchVariantsByProductId(anyInt())).thenReturn(Lists.newArrayList(mockInventory()));
//
//        mvc.perform(get("/api/v1/general/products/search?brand=1"))
//                .andExpect(jsonPath("$.data", hasSize(1)))
//                .andExpect(jsonPath("$.data.[0].product_id", is(1)))
//                .andExpect(jsonPath("$.data.[0].product_name", is("Olay")))
//                .andExpect(jsonPath("$.data.[0].product_key", is("2166325370469")))
//                .andExpect(jsonPath("$.data.[0].variants[0].inventory_id", is("OLAAB1111111")))
//                .andExpect(jsonPath("$.message", is("success")))
//                .andExpect(jsonPath("$.total_elements", is(1)))
//                .andExpect(status().isOk());
//
//        verify(hotelsRepo).countProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt());
//        verify(hotelsRepo).findProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt());
//        verify(hotelsRepo).searchVariantsByProductId(anyInt());
//    }
//
//    @Test
//    public void shouldReturnProductsWhenSearchExistingProductByShop() throws Exception {
//        when(hotelsRepo.countProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(1);
//        when(hotelsRepo.findProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt()))
//                .thenReturn(Lists.newArrayList(mockProduct()));
//        when(hotelsRepo.searchVariantsByProductId(anyInt())).thenReturn(Lists.newArrayList(mockInventory()));
//
//        mvc.perform(get("/api/v1/general/products/search?shop=1"))
//                .andExpect(jsonPath("$.data", hasSize(1)))
//                .andExpect(jsonPath("$.data.[0].product_id", is(1)))
//                .andExpect(jsonPath("$.data.[0].product_name", is("Olay")))
//                .andExpect(jsonPath("$.data.[0].product_key", is("2166325370469")))
//                .andExpect(jsonPath("$.data.[0].variants[0].inventory_id", is("OLAAB1111111")))
//                .andExpect(jsonPath("$.message", is("success")))
//                .andExpect(jsonPath("$.total_elements", is(1)))
//                .andExpect(status().isOk());
//
//        verify(hotelsRepo).countProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt());
//        verify(hotelsRepo).findProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt());
//        verify(hotelsRepo).searchVariantsByProductId(anyInt());
//    }
//
//    @Test
//    public void shouldReturnProductsWhenSearchExistingProductByProduct() throws Exception {
//        when(hotelsRepo.countProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(1);
//        when(hotelsRepo.findProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt()))
//                .thenReturn(Lists.newArrayList(mockProduct()));
//        when(hotelsRepo.searchVariantsByProductId(anyInt())).thenReturn(Lists.newArrayList(mockInventory()));
//
//        mvc.perform(get("/api/v1/general/products/search?product=1"))
//                .andExpect(jsonPath("$.data", hasSize(1)))
//                .andExpect(jsonPath("$.data.[0].product_id", is(1)))
//                .andExpect(jsonPath("$.data.[0].product_name", is("Olay")))
//                .andExpect(jsonPath("$.data.[0].product_key", is("2166325370469")))
//                .andExpect(jsonPath("$.data.[0].variants[0].inventory_id", is("OLAAB1111111")))
//                .andExpect(jsonPath("$.message", is("success")))
//                .andExpect(jsonPath("$.total_elements", is(1)))
//                .andExpect(status().isOk());
//
//        verify(hotelsRepo).countProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt());
//        verify(hotelsRepo).findProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt());
//        verify(hotelsRepo).searchVariantsByProductId(anyInt());
//    }
//
//    @Test
//    public void shouldReturnProductsWhenSearchExistingProductByVendor() throws Exception {
//        when(hotelsRepo.countProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(1);
//        when(hotelsRepo.findProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt()))
//                .thenReturn(Lists.newArrayList(mockProduct()));
//        when(hotelsRepo.searchVariantsByProductId(anyInt())).thenReturn(Lists.newArrayList(mockInventory()));
//
//        mvc.perform(get("/api/v1/general/products/search?vendor=1"))
//                .andExpect(jsonPath("$.data", hasSize(1)))
//                .andExpect(jsonPath("$.data.[0].product_id", is(1)))
//                .andExpect(jsonPath("$.data.[0].product_name", is("Olay")))
//                .andExpect(jsonPath("$.data.[0].product_key", is("2166325370469")))
//                .andExpect(jsonPath("$.data.[0].variants[0].inventory_id", is("OLAAB1111111")))
//                .andExpect(jsonPath("$.message", is("success")))
//                .andExpect(jsonPath("$.total_elements", is(1)))
//                .andExpect(status().isOk());
//
//        verify(hotelsRepo).countProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt());
//        verify(hotelsRepo).findProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt());
//        verify(hotelsRepo).searchVariantsByProductId(anyInt());
//    }
//
//    @Test
//    public void shouldReturnProductsWhenSearchExistingProductWithParams() throws Exception {
//        when(hotelsRepo.countProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(1);
//        when(hotelsRepo.findProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt()))
//                .thenReturn(Lists.newArrayList(mockProduct()));
//        when(hotelsRepo.searchVariantsByProductId(anyInt())).thenReturn(Lists.newArrayList(mockInventory()));
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("title", "test");
//        params.add("tag", "test");
//        params.add("pline", "test");
//        params.add("brand", "1");
//        params.add("shop", "1");
//        params.add("product", "1");
//        params.add("vendor", "1");
//
//        mvc.perform(get("/api/v1/general/products/search").params(params))
//                .andExpect(jsonPath("$.data", hasSize(1)))
//                .andExpect(jsonPath("$.data.[0].product_id", is(1)))
//                .andExpect(jsonPath("$.data.[0].product_name", is("Olay")))
//                .andExpect(jsonPath("$.data.[0].product_key", is("2166325370469")))
//                .andExpect(jsonPath("$.data.[0].variants[0].inventory_id", is("OLAAB1111111")))
//                .andExpect(jsonPath("$.message", is("success")))
//                .andExpect(jsonPath("$.total_elements", is(1)))
//                .andExpect(status().isOk());
//
//        verify(hotelsRepo).countProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt());
//        verify(hotelsRepo).findProducts(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt());
//        verify(hotelsRepo).searchVariantsByProductId(anyInt());
//    }
//
//    private ProductExDTO mockProductEx() {
//        ProductExDTO dto = new ProductExDTO();
//        dto.setProductId(1);
//        dto.setProductName("Olay");
//        dto.setProductKey("2166325370469");
//        dto.setBrandId(1);
//        dto.setBrandName("brand name");
//
//        return dto;
//    }
//    private ProductModel mockProductModel() {
//        ProductModel dto = new ProductModel();
//        dto.setProductId(1);
//        dto.setProductName("Olay");
//        dto.setProductKey("2166325370469");
//        dto.setBrandId(1);
//        dto.setBrandName("brand name");
//        dto.setCategoryIds(Lists.newArrayList());
//
//        return dto;
//    }
//    private ProductDTO mockProduct() {
//        ProductDTO dto = new ProductDTO();
//        dto.setProductId(1);
//        dto.setProductName("Olay");
//        dto.setProductKey("2166325370469");
//        dto.setBrandId(1);
//        dto.setBrandName("brand name");
//
//        return dto;
//    }
//
//    private List<InventoryDTO> mockInventory() {
//        InventoryDTO inventory = new InventoryDTO();
//        inventory.setInventoryId("OLAAB1111111");
//        inventory.setVariantName("Olay");
//        inventory.setVendorId("3924");
//        inventory.setVendorName("BANGKOK CENTRAL SDO LIMITED");
//
//        return Lists.newArrayList(inventory);
//    }
//}*/
