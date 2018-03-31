package usmp.progra2.ex_auto.examen.web;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import usmp.progra2.ex_auto.examen.model.Auto;

@Controller
public class AutoController {

	public Object getResult(String values) {

		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		Object result = null;
		try {
			result = engine.eval(values);
		} catch (ScriptException e) {
			e.printStackTrace();
		}

		return result;
	}

	@GetMapping("/new")
	public String initCreationForm(Model model) {
		model.addAttribute("auto", new Auto());
		return "auto";
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	@PostMapping("/new")
	public String submitForm(@ModelAttribute Auto carro) {

		double galones = 0;
		try {
			String vehiculo = carro.getAuto();
			int recorrido = carro.getRecorrido();
			switch (vehiculo) {
			case "Vehiculo1":
				galones = recorrido / 40.00;
				break;
			case "Vehiculo2":
				galones = recorrido / 35.00;
				break;
			case "Vehiculo3":
				galones = recorrido / 45.00;
				break;

			default:
				System.out.print("Por favor seleccione un Vehiculo");
				break;
			}

			carro.setConsumo(String.valueOf(round(galones, 2)));
			System.out.print(String.valueOf(galones));

		} catch (Exception e) {
			System.out.print("Por favor seleccione un Vehiculo");
		}

		return "resultado";
	}

}
