# FIX消息解析程序

## 介绍

本程序的功能是将输入的FIX协议消息解析成可读性强的文字说明，输入为FIX消息字符串与FIX.xml配置文件，输出目前支持两种格式：String和Json。

### 输入格式

FIX消息：```8=FIX.4.49=10035=D11=1234540=154=138=500060=2003061501:14:4910=127```

输入配置文件：```FIX.xml```

### 输出格式

简单字符串格式：
```
BeginString => FIX.4.4
BodyLength => 100
MsgType => ORDER_SINGLE
ClOrdID => 12345
OrdType => MARKET
Side => BUY
OrderQty => 5000
TransactTime => 2003061501:14:49
CheckSum => 127
```

Json格式：
```
{
    "BeginString":"FIX.4.4",
    "BodyLength":"100",
    "MsgType":"ORDER_SINGLE",
    "ClOrdID":"12345",
    "OrdType":"MARKET",
    "Side":"BUY",
    "OrderQty":"5000",
    "TransactTime":"2003061501:14:49",
    "CheckSum":"127"
}
```
