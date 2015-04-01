package dk.atbrask.ecoteckremotecontrol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.ConsumerIrManager;

public class EcoteckIntentReceiver extends BroadcastReceiver
{
    private static final int[] IR_Temperature_Up = new int[] {8, 3, 4, 2, 1, 1, 1, 2, 2, 1, 2, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 2};
    private static final int[] IR_Temperature_Down = new int[] {8, 3, 4, 2, 1, 1, 1, 2, 4, 2, 1, 1, 1, 2, 5, 2, 2 };
    private static final int[] IR_Fire_Up = new int[] {8, 3, 4, 2, 1, 1, 1, 2, 5, 1, 1, 1, 1, 2, 4, 1, 1, 1, 2};
    private static final int[] IR_Fire_Down = new int[] {8, 3, 4, 2, 1, 1, 1, 2, 3, 1, 1, 1, 1, 1, 1, 2, 3, 2, 1, 1, 2};
    private static final int[] IR_On_Off = new int[] {8, 3, 4, 2, 1, 1, 1, 2, 2, 1, 1, 2, 1, 1, 1, 2, 2, 1, 2, 2, 2};
    private static final int modulation = 35874;
    private static final int pulseLength = 842;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals("dk.atbrask.EcoteckWidget.intent.action.TEMPERATURE_UP"))
        {
            sendIRCode(context, IR_Temperature_Up);
            EcoteckWidget.refreshButtonListeners(context);
        }
        else if (intent.getAction().equals("dk.atbrask.EcoteckWidget.intent.action.TEMPERATURE_DOWN"))
        {
            sendIRCode(context, IR_Temperature_Down);
            EcoteckWidget.refreshButtonListeners(context);
        }
        else if (intent.getAction().equals("dk.atbrask.EcoteckWidget.intent.action.FIRE_UP"))
        {
            sendIRCode(context, IR_Fire_Up);
            EcoteckWidget.refreshButtonListeners(context);
        }
        else if (intent.getAction().equals("dk.atbrask.EcoteckWidget.intent.action.FIRE_DOWN"))
        {
            sendIRCode(context, IR_Fire_Down);
            EcoteckWidget.refreshButtonListeners(context);
        }
        else if (intent.getAction().equals("dk.atbrask.EcoteckWidget.intent.action.ON_OFF"))
        {
            EcoteckWidget.showConfirmScreen(context);
            EcoteckWidget.refreshButtonListeners(context);
        }
        else if (intent.getAction().equals("dk.atbrask.EcoteckWidget.intent.action.CONFIRM_ON_OFF"))
        {
            sendIRCode(context, IR_On_Off);
            EcoteckWidget.showMainScreen(context);
            EcoteckWidget.refreshButtonListeners(context);
        }
        else if (intent.getAction().equals("dk.atbrask.EcoteckWidget.intent.action.CANCEL_ON_OFF"))
        {
            EcoteckWidget.showMainScreen(context);
            EcoteckWidget.refreshButtonListeners(context);
        }
    }

    private void sendIRCode(Context context, int[] code)
    {
        int[] signal = new int[code.length];
        for (int i = 0; i < signal.length; i++)
            signal[i] = pulseLength * code[i];

        ConsumerIrManager ir = (ConsumerIrManager)context.getSystemService(Context.CONSUMER_IR_SERVICE);
        //todo: Check for IR hardware capabilities
        ir.transmit(modulation, signal);
    }
}
