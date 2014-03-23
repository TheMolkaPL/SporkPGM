package me.raino.module;

import java.util.List;

import me.raino.module.exceptions.ModuleLoadException;

import com.google.common.collect.Lists;

public class ModuleRegistry {
	private static List<ModuleInfo> modules = Lists.newArrayList();

	public static void register(Class<? extends Module> clazz) throws ModuleLoadException {

		if (!clazz.isAnnotationPresent(ModuleInfo.class))
			throw new ModuleLoadException("Module " + clazz + " must have a ModuleInfo annotation present to be valid!");

		modules.add(clazz.getAnnotation(ModuleInfo.class));
	}

	public static List<ModuleInfo> getModules() {
		return modules;
	}
}
