# Topshiriq №5 — Xulq-atvor andozalari

**Talaba:** Xamroyeva Gulruh  
**Guruh:** 1-1DIM-24  
**Fan:** Dasturlash texnologiyalari  
**Universitet:** Buxoro davlat universiteti, 2026  
**GitHub:** https://github.com/Gulcha777/topshiriq5-behavioral

## Mavzu

- **Nazariy:** Loyiha andozalari — III: xulq-atvor (Observer, Strategy, Command) andozalari
- **Amaliy:** Observer va Strategy andozalari yordamida hodisalarni boshqaruvchi modul ishlab chiqish

## Vazifalar

| Vazifa | Andoza | Paket |
|--------|--------|-------|
| Vazifa 1: WeatherStation → PhoneDisplay, WebDisplay, LoggerObserver; observerni bekor qilish | Observer | `src/main/java/vazifa1` |
| Vazifa 2: Jo'natish narxi (Standart, Ekspress, Xalqaro); runtime'da almashtirish | Strategy | `src/main/java/vazifa2` |
| Vazifa 3: EventBus | Observer + Strategy | `src/main/java/vazifa3/eventbus` |
| Vazifa 3 (qo'shimcha): matn muharririda Undo/Redo | Command | `src/main/java/vazifa3/editor` |

## Loyiha tuzilmasi

```
topshiriq5-behavioral/
├── src/
│   ├── main/java/
│   │   ├── vazifa1/            # Observer: WeatherStation va kuzatuvchilar
│   │   ├── vazifa2/            # Strategy: jo'natish narxi
│   │   ├── vazifa3/
│   │   │   ├── eventbus/       # EventBus + yetkazish strategiyalari
│   │   │   └── editor/         # Command: Undo/Redo
│   │   └── demo/Main.java      # demo stsenariy (hammasi ketma-ket)
│   └── test/java/
│       ├── testkit/            # minimal test freymvorki (@Test, Assert, TestRunner)
│       └── tests/              # 33 ta unit test
├── docs/diagrams/              # 3 ta sequence diagramma (PNG)
├── run.sh / run.bat            # demo'ni ishga tushirish
├── test.sh / test.bat          # testlarni ishga tushirish
└── README.md
```

## Ishga tushirish

Talab: **JDK 11 yoki yuqori** (`java -version`). Tashqi kutubxona kerak emas.

**Windows:**
```
run.bat      :: demo stsenariy
test.bat     :: unit testlar
```

**Linux / macOS / Git Bash:**
```
./run.sh     # demo stsenariy
./test.sh    # unit testlar
```

Kutilgan natija: `Natija: 33 ta o'tdi, 0 ta yiqildi, jami 33 ta test.`

## Unit testlar (33 ta)

| Sinf | Testlar | Tekshiradi |
|------|---------|------------|
| `WeatherStationTest` | 8 | xabar berish, bekor qilish, dublikat, o'lchovsiz holat, xato qiymat, o'zini chiqarib tashlash |
| `ShippingCalculatorTest` | 7 | 3 ta tarif narxi, runtime almashtirish, yangi strategiya, noto'g'ri kirish |
| `EventBusTest` | 10 | mavzu, wildcard, obunani bekor qilish, FIFO/Priority/LIFO, xato izolyatsiyasi |
| `CommandManagerTest` | 8 | undo/redo, redo tozalanishi, bo'sh tarix, xato buyruq, EventBus integratsiyasi |

## Sequence diagrammalar

`docs/diagrams/`: `sequence_observer.png`, `sequence_strategy.png`, `sequence_command.png`

## SOLID

- **SRP** — har bir sinf bitta vazifa (`WeatherStation` faqat holat va xabar, `LoggerObserver` faqat log)
- **OCP** — yangi kuzatuvchi / tarif / yetkazish strategiyasi mavjud kodni o'zgartirmasdan qo'shiladi
- **LSP** — barcha strategiyalar va kuzatuvchilar o'z interfeysini to'liq almashtira oladi
- **ISP** — kichik interfeyslar (`WeatherObserver`, `ShippingStrategy`, `EventListener`, `Command`)
- **DIP** — `ShippingCalculator`, `EventBus`, `CommandManager` konkret sinflarga emas, abstraksiyalarga bog'liq
