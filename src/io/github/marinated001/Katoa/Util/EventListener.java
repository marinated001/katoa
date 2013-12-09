package io.github.marinated001.Katoa.Util;

import io.github.marinated001.Katoa.Katoa;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class EventListener implements Listener
{
	public static ArrayList<Listener>	listeners	= new ArrayList<Listener>();

	public static void registerEvents(Listener L) {
		Bukkit.getPluginManager().registerEvents(L, Katoa.getInstance());
		listeners.add(L);
	}
}