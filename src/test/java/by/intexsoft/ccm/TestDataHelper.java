package by.intexsoft.ccm;

import by.intexsoft.ccm.model.db.ActionType;
import by.intexsoft.ccm.model.db.Pack;
import by.intexsoft.ccm.model.db.PackHistory;
import by.intexsoft.ccm.model.transfer.PackHistoryItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestDataHelper
{

	public static PackHistory createPackHistory()
	{
		PackHistory result = new PackHistory();
		result.identity = 1;
		result.action = ActionType.ACTIVATE;
		result.subsId = 1;
		result.traceNumber = 1;
		result.pack = new Pack();
		result.pack.identity = 0;
		result.pack.name = "Test";
		return result;
	}

	private static Pack createPack()
	{
		Pack result = new Pack();
		result.name = "Test";
		result.identity = 1;
		return result;
	}

	public static List<Pack> getPackList(int count)
	{
		List<Pack> result = new ArrayList<>(count);
		for (int i = 0; i < count; i++)
		{
			result.add(createPack());
		}
		return result;
	}

	public static Date convertJsonDateToDate(String input)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
		try
		{
			return simpleDateFormat.parse(input);
		}
		catch (ParseException e)
		{
			return null;
		}
	}

	private static PackHistoryItem createPackHistoryItem()
	{
		PackHistoryItem result = new PackHistoryItem();
		result.packId = 1;
		result.name = "Test";
		result.start = new Date();
		result.type = ActionType.ACTIVATE;
		result.end = new Date();
		return result;
	}

	public static List<PackHistoryItem> getPackHistoryItemList(int count)
	{
		List<PackHistoryItem> result = new ArrayList<>(count);
		for (int i = 0; i < count; i++)
		{
			result.add(createPackHistoryItem());
		}
		return result;
	}
}
