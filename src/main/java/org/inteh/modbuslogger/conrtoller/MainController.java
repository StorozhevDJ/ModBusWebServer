package org.inteh.modbuslogger.conrtoller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import org.inteh.modbuslogger.dao.DeviceInfoDBListDAO;
import org.inteh.modbuslogger.database.model.DbDataMlpModel;
import org.inteh.modbuslogger.mapper.ViewMlpDataMapper;
import org.inteh.modbuslogger.modbus.model.ModbusDeviceInfoModel;
import org.inteh.modbuslogger.modbus.model.ModbusMLPDataModel;
import org.inteh.modbuslogger.model.ViewStatusModel;
import org.inteh.modbuslogger.service.DeviceServiceImpl;
import org.inteh.modbuslogger.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.server.ResponseStatusException;

@Controller
public class MainController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private DeviceInfoDBListDAO deviceDBListDAO;
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
		LOGGER.info("Открытие страницы информации об устройсвах");

		// (1) (en)
		// After user login successfully.
		// (vi)
		// Sau khi user login thanh cong se co principal
		String userName = principal.getName();

		System.out.println("User Name: " + userName);

		User loginedUser = (User) ((Authentication) principal).getPrincipal();

		String userInfo = WebUtils.toString(loginedUser);
		model.addAttribute("userInfo", userInfo);

		String[] ports = deviceServiceImpl.getAvailablePorts();
		model.addAttribute("ports", ports);
		LOGGER.info("Доступные порты {}", String.join(", ", ports));

		List<ModbusDeviceInfoModel> list = deviceDBListDAO.getDevicesDBList();
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
		LOGGER.info("Открытие страницы информации о МЛП");
		String userName = principal.getName();

		System.out.println("User Name: " + userName);

		User loginedUser = (User) ((Authentication) principal).getPrincipal();

		String userInfo = WebUtils.toString(loginedUser);
		System.out.print(loginedUser.getAuthorities());
		model.addAttribute("userInfo", userInfo);

		List<ModbusDeviceInfoModel> list = deviceDBListDAO.getDevicesDBList(150);
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
	
	/**
	 * Get all device from DataBase
	 * @return List<DeviceInfoModbus>
	 */
	@RequestMapping(value = "/devices", //
			method = RequestMethod.GET, //
			produces = { MediaType.APPLICATION_JSON_VALUE, //
					MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public List<ModbusDeviceInfoModel> getDevices() {
		LOGGER.info("Принят запрос отображения списка устройств из БД");
		List<ModbusDeviceInfoModel> list = deviceDBListDAO.getDevicesDBList();
		return list;
	}

	/**
	 * Get 
	 * @param devNo
	 * @return
	 */
	// @GetMapping("{id:\\d+}")
	@RequestMapping(value = "/device/{devNo}", // Find device in DB
			method = RequestMethod.GET, //
			produces = { MediaType.APPLICATION_JSON_VALUE, //
					MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ModbusDeviceInfoModel getDevice(@PathVariable("devNo") short devNo) {
		System.out.println("URL open dev " + devNo);

		return deviceDBListDAO.getDevice(devNo);
	}

	/**
	 * Find device in ModBus line
	 * @param port
	 * @param devNo
	 * @return
	 */
	// @GetMapping("{id:\\d+}")
	@RequestMapping(value = "/device/search/{port}/{devNo}",
			method = RequestMethod.GET, //
			produces = { MediaType.APPLICATION_JSON_VALUE, //
					MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ModbusDeviceInfoModel getSearchDevice(@PathVariable("port") String port, @PathVariable("devNo") short devNo) {
		// System.out.println("URL open dev " + devNo);
		// LOGGER.log(Level.WARNING, "Запрос поиска устройства {} на порту {}", devNo,
		// port);
		LOGGER.info("Принята команда поиска устройства {}, port {}.", devNo, port);
		return deviceServiceImpl.loadSearchedDevice(port, devNo);
	}

	// URL:
	@RequestMapping(value = "/device/mlp/{port}/{devNo}", // Read device data in ModBus line
			method = RequestMethod.GET, //
			produces = { MediaType.APPLICATION_JSON_VALUE, //
					MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ModbusMLPDataModel getMLPModBusData(@PathVariable("port") String port, @PathVariable("devNo") short devNo) {
		// System.out.println("URL open dev " + devNo);
		LOGGER.info("Принята команда запроса данных от устройства {}, port {}.", devNo, port);
		ModbusMLPDataModel ret = deviceServiceImpl.loadMLPDeviceData(port, 9600, devNo);
		// LOGGER.log(Level.WARNING, "Результат " + ret == null ? " ret == null " :
		// ret.toString());
		if (ret == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Device Not Found");
		}
		return ret;

	}



	/**
	 * Add device in to DB
	 * @param port
	 * @param addr
	 * @return
	 */
	@RequestMapping(value = "/device/search/{port}/{devNo}", //
			method = RequestMethod.POST, //
			produces = { MediaType.APPLICATION_JSON_VALUE, //
					MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<?> addDeviceToDatabase(@PathVariable("port") String port, @PathVariable("devNo") short addr) {
		LOGGER.info("Received command to add device with port: {}, address: {} in to Database", port, addr);
		ViewStatusModel status = deviceServiceImpl.addDeviceToDb(port, addr);
		return new ResponseEntity<>(status, status.getStatusCode()==0?HttpStatus.CREATED:HttpStatus.ACCEPTED);
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
	public ModbusDeviceInfoModel updateDevice(@RequestBody ModbusDeviceInfoModel dev) {

		System.out.println("(Service Side) Editing employee: " + dev.getAddress());

		return deviceDBListDAO.updateDevice(dev);
	}

	/**
	 * Deleting Device from DataBase by Device ID
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/device/{devId}", //
			method = RequestMethod.DELETE, //
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ViewStatusModel deleteDevice(@PathVariable("devId") short id) {
		LOGGER.info("Deleting device with ID: {}", id);
		return deviceServiceImpl.deleteDeviceFromDb(id);
	}

	/**
	 * Get MLP Device data
	 * @param devNo
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	@RequestMapping(value = "/data/mlp/{devNo}", // Read device data in DataBase
			method = RequestMethod.GET, //
			produces = { MediaType.APPLICATION_JSON_VALUE, //
					MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public List<ViewMlpDataMapper> getMLPDBData(@PathVariable("devNo") short devNo,
			@RequestParam(value = "from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
			@RequestParam(value = "to", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {
		return deviceDBListDAO.getMLPData(devNo, fromDate, toDate);
	}
}
