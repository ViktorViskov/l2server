/*
 * Copyright (c) 2013 L2jMobius
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 * IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package custom.NoblessMaster;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.quest.QuestSound;

import ai.AbstractNpcAI;

/**
 * @author Mobius
 */
public class NoblessMaster extends AbstractNpcAI
{
	// Item
	private static final int NOBLESS_TIARA = 7694;
	
	private NoblessMaster()
	{
		addStartNpc(Config.NOBLESS_MASTER_NPCID);
		addTalkId(Config.NOBLESS_MASTER_NPCID);
		addFirstTalkId(Config.NOBLESS_MASTER_NPCID);
	}
	
	@Override
	public String onEvent(String event, Npc npc, Player player)
	{
		if (!Config.NOBLESS_MASTER_ENABLED)
		{
			return null;
		}
		
		switch (event)
		{
			case "noblesse":
			{
				if (player.isNoble())
				{
					return "1003000-3.htm";
				}
				if (player.getLevel() >= Config.NOBLESS_MASTER_LEVEL_REQUIREMENT)
				{
					if (Config.NOBLESS_MASTER_REWARD_TIARA)
					{
						giveItems(player, NOBLESS_TIARA, 1);
					}
					player.setNoble(true);
					player.sendPacket(QuestSound.ITEMSOUND_QUEST_FINISH.getPacket());
					return "1003000-1.htm";
				}
				return "1003000-2.htm";
			}
		}
		
		return null;
	}
	
	@Override
	public String onFirstTalk(Npc npc, Player player)
	{
		return "1003000.htm";
	}
	
	public static void main(String[] args)
	{
		new NoblessMaster();
	}
}
