package org.inteh.sbsecurity.conrtoller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import org.inteh.sbsecurity.dao.DeviceInfoDBListDAO;
import org.inteh.sbsecurity.dao.modbus.ModBusDAO;
import org.inteh.sbsecurity.model.DeviceInfo;
import org.inteh.sbsecurity.model.MLPData;
import org.inteh.sbsecurity.service.DeviceServiceImpl;
import org.inteh.sbsecurity.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

	@Autowired
	private DeviceInfoDBListDAO DeviceDBListDAO;
	@Autowired
	private DeviceServiceImpl deviceServiceImpl;

	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	public String welcomePage(Model model) {
		model.addAttribute("title", "Welcome");
		model.addAttribute("message", "Это главная страница!");
		return "welcomePage";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminPage(Model model, Principal principal) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();

		String userInfo = WebUtils.toString(loginedUser);

		model.addAttribute("userInfo", userInfo);

		model.addAttribute("title", "Администрирование");

		return "adminPage";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Model model) {
		model.addAttribute("title", "Вход в систему");

		return "loginPage";
	}

	@RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
	public String logoutSuccessfulPage(Model model) {
		model.addAttribute("title", "Logout");
		return "logoutSuccessfulPage";
	}

	@RequestMapping(value = "/devInfo", method = RequestMethod.GET)
	public String userInfo(Model model, Principal principal) {

		// (1) (en)
		// After user login successfully.
		// (vi)
		// Sau khi user login thanh cong se co principal
		String userName = principal.getName();

		System.out.println("User Name: " + userName);

		User loginedUser = (User) ((Authentication) principal).getPrincipal();

		String userInfo = WebUtils.toString(loginedUser);
		model.addAttribute("userInfo", userInfo);

		String[] ports = ModBusDAO.getPortNames();
		model.addAttribute("ports", ports);

		List<DeviceInfo> list = DeviceDBListDAO.getDevicesDBList();
		model.addAttribute("deviceInfos", list);

		Iterator<?> iterator = loginedUser.getAuthorities().iterator();
		while (iterator.hasNext()) {
			if (iterator.next().toString().equals("ROLE_ADMIN")) {
				model.addAttribute("admin", "true");
			}
		}

		return "devInfoPage";
	}

	@RequestMapping(value = "/devMLP", method = RequestMethod.GET)
	public String devMLP(Model model, Principal principal) {
		String userName = principal.getName();

		System.out.println("User Name: " + userName);

		User loginedUser = (User) ((Authentication) principal).getPrincipal();

		String userInfo = WebUtils.toString(loginedUser);
		System.out.print(loginedUser.getAuthorities());
		model.addAttribute("userInfo", userInfo);
		
		List<DeviceInfo> list = DeviceDBListDAO.getDevicesDBList("МЛП");
		model.addAttribute("deviceInfos", list);

		Iterator<?> iterator = loginedUser.getAuthorities().iterator();
		while (iterator.hasNext()) {
			if (iterator.next().toString().equals("ROLE_ADMIN")) {
				model.addAttribute("admin", "true");
			}
		}

		return "devMLPPage";
	}

	@RequestMapping(value = "/devSKCT", method = RequestMethod.GET)
	public String devSKCT(Model model, Principal principal) {
		String userName = principal.getName();

		System.out.println("User Name: " + userName);

		User loginedUser = (User) ((Authentication) principal).getPrincipal();

		String userInfo = WebUtils.toString(loginedUser);
		System.out.print(loginedUser.getAuthorities());
		model.addAttribute("userInfo", userInfo);

		Iterator<?> iterator = loginedUser.getAuthorities().iterator();
		while (iterator.hasNext()) {
			if (iterator.next().toString().equals("ROLE_ADMIN")) {
				model.addAttribute("admin", "true");
			}
		}

		return "devSKCTPage";
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Model model, Principal principal) {

		if (principal != null) {
			User loginedUser = (User) ((Authentication) principal).getPrincipal();

			String userInfo = WebUtils.toString(loginedUser);

			model.addAttribute("userInfo", userInfo);

			String message = "Hi " + principal.getName() + " Authorities=" + loginedUser.getAuthorities()//
					+ "<br> You do not have permission to access this page!";
			model.addAttribute("message", message);

		}

		return "403Page";
	}

	// -------------------------- Restful Controller ----------------------------
	// URL:
	// http://localhost:8080/SomeContextPath/devices
	// http://localhost:8080/SomeContextPath/devices.xml
	// http://localhost:8080/SomeContextPath/devices.json
	@RequestMapping(value = "/devices", //
			method = RequestMethod.GET, //
			produces = { MediaType.APPLICATION_JSON_VALUE, //
					MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public List<DeviceInfo> getDevices() {
		List<DeviceInfo> list = DeviceDBListDAO.getDevicesDBList();
		return list;
	}

	// URL:
	// http://localhost:8080/SomeContextPath/device/{devNo}
	// http://localhost:8080/SomeContextPath/device/{devNo}.xml
	// http://localhost:8080/SomeContextPath/device/{devNo}.json
	// @GetMapping("{id:\\d+}")
	@RequestMapping(value = "/device/{devNo}", // Find device in DB
			method = RequestMethod.GET, //
			produces = { MediaType.APPLICATION_JSON_VALUE, //
					MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public DeviceInfo getDevice(@PathVariable("devNo") short devNo) {
		System.out.println("URL open dev " + devNo);
		return DeviceDBListDAO.getDevice(devNo);
	}

	// URL:
	// http://localhost:8080/SomeContextPath/device/{devNo}
	// http://localhost:8080/SomeContextPath/device/{devNo}.xml
	// http://localhost:8080/SomeContextPath/device/{devNo}.json
	// @GetMapping("{id:\\d+}")
	@RequestMapping(value = "/device/search/{port}/{devNo}", // Find device in ModBus line
			method = RequestMethod.GET, //
			produces = { MediaType.APPLICATION_JSON_VALUE, //
					MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public DeviceInfo getSearchDevice(@PathVariable("port") String port, @PathVariable("devNo") short devNo) {
		System.out.println("URL open dev " + devNo);
		return deviceServiceImpl.loadSearchedDevice(port, devNo);
	}

	// URL:
	@RequestMapping(value = "/device/mlp/{port}/{devNo}", // Read device data in ModBus line
			method = RequestMethod.GET, //
			produces = { MediaType.APPLICATION_JSON_VALUE, //
					MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public MLPData getMLPModBusData(@PathVariable("port") String port, @PathVariable("devNo") short devNo) {
		System.out.println("URL open dev " + devNo);
		return deviceServiceImpl.loadMLPDeviceData(port, devNo);
	}

	// URL:
	// http://localhost:8080/SomeContextPath/device
	// http://localhost:8080/SomeContextPath/device.xml
	// http://localhost:8080/SomeContextPath/device.json

	@RequestMapping(value = "/device", //
			method = RequestMethod.POST, //
			produces = { MediaType.APPLICATION_JSON_VALUE, //
					MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public DeviceInfo addDevice(@RequestBody DeviceInfo dev) {
		System.out.println("(Service Side) Creating device: " + dev.getAddress());
		return DeviceDBListDAO.addDevice(dev);
	}

	// URL:
	// http://localhost:8080/SomeContextPath/employee
	// http://localhost:8080/SomeContextPath/employee.xml
	// http://localhost:8080/SomeContextPath/employee.json
	@RequestMapping(value = "/device", //
			method = RequestMethod.PUT, //
			produces = { MediaType.APPLICATION_JSON_VALUE, //
					MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public DeviceInfo updateEmployee(@RequestBody DeviceInfo dev) {

		System.out.println("(Service Side) Editing employee: " + dev.getAddress());

		return DeviceDBListDAO.updateDevice(dev);
	}

	// URL:
	// http://localhost:8080/SomeContextPath/employee/{empNo}
	@RequestMapping(value = "/device/{devNo}", //
			method = RequestMethod.DELETE, //
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public void deleteEmployee(@PathVariable("devNo") short devNo) {

		System.out.println("(Service Side) Deleting employee: " + devNo);

		DeviceDBListDAO.deleteDevice(devNo);
	}

	// URL:
	@RequestMapping(value = "/data/mlp/{devNo}", // Read device data in DataBase
			method = RequestMethod.GET, //
			produces = { MediaType.APPLICATION_JSON_VALUE, //
					MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public List<MLPData> getMLPDBData(@PathVariable("devNo") short devNo,
			@RequestParam(value = "from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
			@RequestParam(value = "to", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {
		return DeviceDBListDAO.getMLPData(devNo, fromDate, toDate);
	}
}
