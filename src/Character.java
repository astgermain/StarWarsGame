import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Graphics2D;
import java.awt.image.*;
import java.util.*;
import java.awt.geom.*;
import java.io.*;

public class Character
{
    private int hp, exp, attack, type;
    private double crit;
    

    public Character()
    {
    	hp = 10;
        exp = 0;
        attack = 2;
        type = 1;
        crit = .15;
    }
    public void setHp(int newHp)
    {
        this.hp = newHp;
    }
    public void setExp(int newExp)
    {
        this.exp = newExp;
    }
    public void setAttack(int newAttack)
    {
        this.attack = newAttack;
    }
    public void setType(int newType)
    {
        this.type = newType;
    }
    public void setCrit(int newCrit)
    {
        this.crit = newCrit;
    }
    
    
    
}