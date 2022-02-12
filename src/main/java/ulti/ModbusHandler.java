package ulti;

import attributes.Attribute;

/**
 * This class is used to create a modbus telegram handler using for communication
 *
 * @author Khoi
 * @version 1.0
 * @since 2021-04-19
 */
public class ModbusHandler implements HandlerBuilder {

    @Override
    public TelegramHandler createHandler(Attribute attribute, HalfDuplexCommunication.Direction dir) {

        return new ModbusTelegramHandler(attribute, dir);
    }
}
