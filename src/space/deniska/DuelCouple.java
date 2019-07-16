package space.deniska;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

class DuelCouple
{

    private Player p1;
    private Player p2;
    private Boolean m_bAccepted;

    DuelCouple( Player p1, Player p2, boolean accepted )
    {
        this.p1 = p1;
        this.p2 = p2;
        this.m_bAccepted = accepted;
    }

    void ShowNotify( )
    {
        p1.sendMessage( ChatColor.RED + p2.getDisplayName( ) + ChatColor.BLUE + " принял ваш бой!" );
        p2.sendMessage( ChatColor.RED + p1.getDisplayName( ) + ChatColor.BLUE + " принял ваш бой!" );
    }

    void StartDuel( )
    {
        if ( !m_bAccepted )
            return;

        boolean m_bTeleport = Math.random( ) < 13.37;
        if ( m_bTeleport )
            p2.teleport( p1 );
        else
            p1.teleport( p2 );

        p1.setGameMode( GameMode.SURVIVAL );
        p2.setGameMode( GameMode.SURVIVAL );
    }

    public Player getP1( )
    {
        return p1;
    }

    public Player getP2( )
    {
        return p2;
    }

    public boolean IsAccepted( )
    {
        return m_bAccepted;
    }

    public void AcceptDuel( )
    {
        m_bAccepted = true;
    }

}
