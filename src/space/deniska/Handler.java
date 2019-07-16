package space.deniska;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.List;

public class Handler implements CommandExecutor, Listener
{

    private Duels plugin;
    private static List< DuelCouple > players = new ArrayList< >( );

    Handler( Duels main )
    {
        this.plugin = main;
    }

    @Override
    public boolean onCommand( CommandSender commandSender, Command command, String s, String[] strings )
    {
        if ( !( commandSender instanceof Player ) )
        {
            commandSender.sendMessage( ChatColor.RED + "Из консоли нельзя юзать, сорян" );
            return true;
        }

        if ( strings.length != 1 )
        {
            commandSender.sendMessage( ChatColor.RED + "Используй: /duel <ник>" );
            return true;
        }

        if ( Bukkit.getPlayer( strings[0] ) == null || !Bukkit.getPlayer( strings[0] ).isOnline( ) )
        {
            commandSender.sendMessage( ChatColor.RED + "Игрок оффлайн, сражаться можно только с теми, кто сейчас онлайн" );
            return true;
        }

        Player enemy = Bukkit.getPlayer( strings[0] );

        if ( enemy == commandSender )
        {
            commandSender.sendMessage( ChatColor.RED + "Нельзя вызвать на дуэль самого себя" );
            return true;
        }

        boolean m_bStartDuel = false;
        DuelCouple m_pDuel = null;

        for ( DuelCouple couple : players )
        {
            if ( ( couple.getP1().equals( commandSender ) || couple.getP2().equals( commandSender ) ) && !couple.IsAccepted( ) )
            {
                //players.remove( couple );
                m_pDuel = couple;
                m_bStartDuel = true;
            }
        }

        if ( m_bStartDuel )
        {
            m_pDuel.AcceptDuel( );
            m_pDuel.ShowNotify( );
            m_pDuel.StartDuel( );
        }
        else
        {
            players.add( new DuelCouple( ( Player ) commandSender, enemy, false ) );
            commandSender.sendMessage( ChatColor.BLUE + "Ты вызвал на дуэль игрока " + ChatColor.RED + enemy.getDisplayName( ) );
            enemy.sendMessage( ChatColor.RED + ( ( Player ) commandSender ).getDisplayName( ) + ChatColor.BLUE + " Вызвал тебя на дуэль" );
        }

        return true;
    }

    @EventHandler
    public void onKill( PlayerDeathEvent e )
    {
        Player killed = e.getEntity( );
        Player killer = e.getEntity( ).getKiller( );

        DuelCouple m_pDuel = null;

        for ( DuelCouple couple : players )
        {
            if ( ( ( couple.getP1( ).equals( killed ) || couple.getP2( ).equals( killer ) ) && couple.IsAccepted( ) )
                    || ( ( couple.getP1( ).equals( killer ) || couple.getP2( ).equals( killed ) ) && couple.IsAccepted( ) ) )
            {
                m_pDuel = couple;
            }
        }

        if ( players.remove( m_pDuel ) )
        {
            Bukkit.broadcastMessage( ChatColor.RED + killer.getDisplayName( ) + ChatColor.BLUE + " победил в дуэли с " + ChatColor.RED + killed.getDisplayName( ) );
        }
    }

}
