package space.deniska;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Duels extends JavaPlugin
{

    @Override
    public void onEnable( )
    {
        getLogger( ).info( "plugin initializated" );
        getCommand( "duel" ).setExecutor( new Handler( this ) );
        Bukkit.getPluginManager().registerEvents( new Handler( this ), this );
    }

    @Override
    public void onDisable( )
    {
        getLogger( ).info( "Bye! Don't forget to credit violanes" );
    }

}
