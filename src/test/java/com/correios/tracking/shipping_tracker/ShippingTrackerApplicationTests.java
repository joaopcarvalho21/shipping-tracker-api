package com.correios.tracking.shipping_tracker;

import com.correios.tracking.shipping_tracker.controllers.ParcelController;
import com.correios.tracking.shipping_tracker.entities.Parcel;
import com.correios.tracking.shipping_tracker.services.ParcelService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ParcelController.class)
public class ShippingTrackerApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ParcelService parcelService;

	@Test
	public void testGetParcelByTrackingNumber() throws Exception {
		Parcel parcel = new Parcel();
		parcel.setId(1L);
		parcel.setTrackingNumber("12345");
		parcel.setStatus("In Transit");

		Mockito.when(parcelService.getParcelByTrackingNumber("12345")).thenReturn(parcel);

		mockMvc.perform(get("/api/parcels/12345"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.trackingNumber").value("12345"))
				.andExpect(jsonPath("$.status").value("In Transit"));
	}

	@Test
	public void testCreateParcel() throws Exception {
		Parcel parcel = new Parcel();
		parcel.setId(1L);
		parcel.setTrackingNumber("67890");
		parcel.setStatus("Pending");

		Mockito.when(parcelService.saveParcel(any(Parcel.class))).thenReturn(parcel);

		mockMvc.perform(post("/api/parcels")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"trackingNumber\": \"67890\", \"status\": \"Pending\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.trackingNumber").value("67890"))
				.andExpect(jsonPath("$.status").value("Pending"));
	}

	@Test
	public void testUpdateParcelStatus() throws Exception {
		Parcel parcel = new Parcel();
		parcel.setId(1L);
		parcel.setTrackingNumber("12345");
		parcel.setStatus("Delivered");

		Mockito.when(parcelService.updateParcelStatus(anyString(), anyString())).thenReturn(parcel);

		mockMvc.perform(put("/api/parcels/12345/status")
						.contentType(MediaType.APPLICATION_JSON)
						.content("\"Delivered\""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.trackingNumber").value("12345"))
				.andExpect(jsonPath("$.status").value("Delivered"));
	}

	@Test
	public void testDeleteParcel() throws Exception {
		Mockito.doNothing().when(parcelService).deleteParcel("12345");

		mockMvc.perform(delete("/api/parcels/12345"))
				.andExpect(status().isOk());
	}

	@Test
	public void testGetAllParcels() throws Exception {
		Parcel parcel1 = new Parcel();
		parcel1.setId(1L);
		parcel1.setTrackingNumber("12345");
		parcel1.setStatus("In Transit");
		parcel1.setLastUpdate(new Date());

		Parcel parcel2 = new Parcel();
		parcel2.setId(2L);
		parcel2.setTrackingNumber("67890");
		parcel2.setStatus("Delivered");
		parcel2.setLastUpdate(new Date());

		Mockito.when(parcelService.getAllParcels()).thenReturn(Arrays.asList(parcel1, parcel2));

		mockMvc.perform(get("/api/parcels"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].trackingNumber").value("12345"))
				.andExpect(jsonPath("$[0].status").value("In Transit"))
				.andExpect(jsonPath("$[1].trackingNumber").value("67890"))
				.andExpect(jsonPath("$[1].status").value("Delivered"));
	}
}
