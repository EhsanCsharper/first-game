# 🚀 First Space Shooter

یک بازی دوبعدی سبک **Retro Arcade Space Shooter** که با استفاده از **Java** و **LibGDX** ساخته شده است.

هدف اصلی این پروژه علاوه بر ساخت یک بازی ساده، یادگیری عملی مفاهیم برنامه‌نویسی بازی، مدیریت Game Loop، انیمیشن، برخورد، مدیریت دشمن‌ها، Power-Upها و ساختاردهی بهتر کدها بوده است.

---

## 🎮 معرفی بازی

در این بازی بازیکن یک سفینه فضایی را کنترل می‌کند و باید با حرکت در صفحه و شلیک به سمت دشمنان، امتیاز کسب کند.

دشمن‌ها در چند نوع مختلف ظاهر می‌شوند:

- **Normal Enemy** — دشمن معمولی
- **Fast Enemy** — دشمن سریع
- **Tank Enemy** — دشمن مقاوم با HP بیشتر و قابلیت شلیک

با افزایش امتیاز، سرعت و تعداد Spawn دشمن‌ها افزایش پیدا می‌کند و بازی به‌تدریج سخت‌تر می‌شود.

در هنگام نابودی دشمن، ممکن است Explosion ایجاد شود و گاهی نیز یک Power-Up در محل دشمن ظاهر شود.

Power-Upهای فعلی:

- ❤️ افزایش جان بازیکن
- ⚡ Rapid Fire — افزایش سرعت شلیک برای مدت محدود

---

# 🕹️ کنترل بازی

| کلید | عملکرد |
|------|--------|
| ⬆️ Up | حرکت به بالا |
| ⬇️ Down | حرکت به پایین |
| ⬅️ Left | حرکت به چپ |
| ➡️ Right | حرکت به راست |
| Space | شلیک |
| P | توقف / ادامه بازی |
| R | شروع مجدد بعد از Game Over |

---

# 📚 LibGDX چیست؟

**LibGDX** یک Framework متن‌باز برای ساخت بازی‌های دوبعدی و سه‌بعدی با زبان Java است. این Framework بسیاری از امکانات موردنیاز بازی‌سازی مانند مدیریت ورودی کاربر، رسم تصاویر، Texture، Animation، صدا، زمان‌بندی و مدیریت چرخه اجرای بازی را در اختیار برنامه‌نویس قرار می‌دهد. مزیت مهم LibGDX این است که می‌توان بخش زیادی از منطق بازی را با Java پیاده‌سازی کرد و برای پلتفرم‌هایی مانند Desktop و Android استفاده کرد.

---

# ⚙️ LibGDX چگونه کار می‌کند؟

LibGDX بر اساس یک **Game Loop** کار می‌کند.

مهم‌ترین متدهای چرخه اجرای بازی عبارت‌اند از:

### `create()`

در ابتدای اجرای بازی یک بار اجرا می‌شود.

در این قسمت معمولاً منابع اولیه بازی ساخته و Load می‌شوند:

```java
batch = new SpriteBatch();
bulletTexture = new Texture("bullet.png");
font = new BitmapFont();
```

### `render()`

مهم‌ترین قسمت بازی است و به صورت مداوم اجرا می‌شود.

در هر Frame معمولاً دو کار انجام می‌شود:

1. **Update کردن وضعیت بازی**
2. **Draw کردن وضعیت جدید**

```text
Input
  ↓
Update Player
  ↓
Update Bullets
  ↓
Update Enemies
  ↓
Check Collisions
  ↓
Update Animations
  ↓
Draw
```

### `dispose()`

در پایان اجرای بازی برای آزاد کردن منابع استفاده می‌شود:

```java
batch.dispose();
texture.dispose();
font.dispose();
```

---

# ⏱️ Delta Time

یکی از مفاهیم مهمی که در این پروژه استفاده شد **Delta Time** است.

Delta Time مدت زمان سپری‌شده بین دو Frame است.

به جای:

```java
playerX += 5;
```

از:

```java
playerX += playerSpeed * delta;
```

استفاده می‌کنیم.

در نتیجه سرعت بازی به تعداد FPS وابسته نخواهد بود و حرکت روی سیستم‌های مختلف رفتار مناسب‌تری خواهد داشت.

---

# 🧱 ساختار کلی پروژه

```text
FirstGame
│
├── Enemy
│   ├── NormalEnemy
│   ├── FastEnemy
│   └── TankEnemy
│
├── EnemySpawner
│
├── Bullet
│   ├── SpaceShipBullet
│   └── TankBullet
│
├── Explosion
│
├── PowerUP
│   ├── HeartPowerUp
│   └── RapidFirePowerUp
│
├── PowerUpManager
│
└── GameState
```

---

# 🧠 مباحثی که در پروژه یاد گرفتیم

## 1. Game Loop

با مفهوم چرخه اصلی اجرای بازی آشنا شدیم:

```text
Input
→ Update
→ Collision
→ Animation
→ Render
```

و یاد گرفتیم چرا منطق بازی باید در هر Frame به‌روزرسانی شود.

## 2. Input Handling

نحوه دریافت ورودی از Keyboard در LibGDX را یاد گرفتیم:

```java
Gdx.input.isKeyPressed(Input.Keys.LEFT);
```

و برای تشخیص فشردن یک‌باره کلید:

```java
Gdx.input.isKeyJustPressed(Input.Keys.P);
```

## 3. حرکت مستقل از FPS

با استفاده از:

```java
speed * delta
```

حرکت بازیکن و سایر موجودات را مستقل از FPS کردیم.

## 4. Object-Oriented Programming

در پروژه از مفاهیم شیءگرایی Java استفاده کردیم.

برای مثال:

```text
Enemy
 ├── NormalEnemy
 ├── FastEnemy
 └── TankEnemy
```

## 5. Inheritance و Polymorphism

از ارث‌بری و Polymorphism برای مدیریت انواع مختلف Enemy و Power-Up استفاده کردیم.

## 6. Collision Detection

برخورد بین Bullet، Enemy، Player، Tank Bullet و Power-Upها را پیاده‌سازی کردیم.

## 7. مدیریت Collectionها

برای نگهداری Bullet و Enemy از `Array` استفاده کردیم و برای حذف امن Objectها هنگام بررسی Collision از Iterator استفاده شد.

## 8. Timer و Spawn System

برای Enemy Spawn، شلیک Player، شلیک Tank، Rapid Fire و Animation از Timer و Delta Time استفاده کردیم.

## 9. Enemy Spawner

منطق Spawn شدن دشمن‌ها از `FirstGame` جدا و در `EnemySpawner` قرار گرفت.

## 10. Difficulty Scaling

با افزایش Score، فاصله Spawn و سرعت برخی دشمن‌ها تغییر می‌کند و بازی سخت‌تر می‌شود.

## 11. Sprite Sheet

برای Animation از Sprite Sheet استفاده کردیم و با:

```java
TextureRegion.split(...)
```

Frameهای آن را جدا کردیم.

## 12. Animation

از:

```java
Animation<TextureRegion>
```

برای Explosion و حرکت سفینه استفاده شد.

## 13. Animation State

برای حرکت سفینه بین حالت‌های Idle، Moving Left و Moving Right جابه‌جا شدیم و نحوه مدیریت Animation غیر Loop را یاد گرفتیم.

## 14. Power-Up System

سیستم Power-Up با ساختار زیر پیاده‌سازی شد:

```text
PowerUP
   │
   ├── HeartPowerUp
   └── RapidFirePowerUp
```

و مدیریت Power-Upها توسط `PowerUpManager` انجام می‌شود.

## 15. Game State

برای مدیریت وضعیت بازی از `GameState` استفاده شد:

```text
PLAYING
PAUSED
GAME_OVER
```

## 16. Refactoring

بخشی از منطق بازی، مانند مدیریت Enemyها، از `FirstGame` جدا و در کلاس‌های مستقل قرار گرفت تا مسئولیت کلاس‌ها مشخص‌تر شود.

---

# 🧩 چالش‌های پروژه

### Sprite Sheet و اندازه Frame

هماهنگ کردن اندازه واقعی Sprite Sheet با اندازه Frame مورد انتظار در کد یکی از چالش‌های اصلی بود.

### Animation حرکت سفینه

برای حرکت سفینه از Sprite Sheetهای جداگانه چپ و راست استفاده کردیم و Animation را به‌صورت Non-Looping مدیریت کردیم.

### مشکل نمایش Frameهای Animation

در Animation حرکت سفینه با مشکلی مواجه شدیم که بعضی Frameها در یک جهت نمایش داده نمی‌شدند. علت مربوط به مدیریت وضعیت `moving` و Reset شدن زمان Animation بود.

### هماهنگ کردن Flame و سفینه

Flame موتور به صورت Sprite جداگانه طراحی شد. چالش اصلی هماهنگ کردن محل Flame با Frameهای مختلف سفینه بود، مخصوصاً به دلیل وجود دو خروجی موتور.

### مدیریت حذف Objectها

در هنگام Collision ممکن است Enemy یا Bullet باید حذف شود. برای حذف امن هنگام Iteration از Iterator استفاده کردیم.

### مدیریت Timerها

برای Enemy Spawn، Player Shoot، Tank Shoot، Rapid Fire، Explosion و Animation نیاز به Timer داشتیم.

### مدیریت منابع

Textureها، SpriteBatch و سایر منابع باید در زمان مناسب Dispose شوند تا منابع حافظه آزاد شوند.

---

# 🎨 Assetها

Assetهای پروژه با سبک:

**Retro Arcade Space Shooter / Pixel Art**

طراحی شده‌اند.

Assetهای اصلی:

```text
spaceship.png
bullet.png
enemy.png
tank.png
tank-bullet.png
explosion-spritesheet.png
heart.png
background.png
spaceship-left-spritesheet.png
spaceship-right-spritesheet.png
```

---

# ▶️ اجرای پروژه

## پیش‌نیازها

- Java
- Gradle
- IntelliJ IDEA یا Android Studio
- فایل‌های پروژه LibGDX

## اجرای پروژه با IDE

1. پروژه را در IDE باز کنید.
2. پروژه را به عنوان یک **Gradle Project** Import کنید.
3. اجازه دهید Gradle Dependencyها را دریافت و پروژه را Build کند.
4. Launcher مربوط به نسخه Desktop پروژه را پیدا کنید.
5. Launcher را اجرا کنید.

در پروژه‌های جدید LibGDX معمولاً Launcher دسکتاپ چیزی مشابه:

```text
Lwjgl3Launcher
```

است.

نام دقیق Module و Launcher به Template پروژه بستگی دارد.

## اجرای پروژه با Gradle

اگر پروژه دارای Module دسکتاپ `lwjgl3` باشد، در Windows:

```bash
./gradlew.bat lwjgl3:run
```

و در Linux / macOS:

```bash
./gradlew lwjgl3:run
```

استفاده می‌شود.

اگر نام Module دسکتاپ متفاوت باشد، باید Task مربوط به همان Module اجرا شود.

---

# 📁 محل Assetها

تمام Textureها و Assetهای بازی باید در مسیر Asset مورد استفاده توسط پروژه قرار داشته باشند.

برای مثال:

```java
new Texture("spaceship.png");
```

فایل را از مسیر Assetهای بازی Load می‌کند.

بنابراین نام فایل و محل قرارگیری آن باید با چیزی که در کد استفاده شده هماهنگ باشد.

---

# 🏗️ روند کلی اجرای بازی

```text
          ┌──────────────┐
          │    Input     │
          └──────┬───────┘
                 ↓
        ┌─────────────────┐
        │ Update Player   │
        └────────┬────────┘
                 ↓
        ┌─────────────────┐
        │ Update Bullets  │
        └────────┬────────┘
                 ↓
        ┌─────────────────┐
        │ Update Enemies  │
        └────────┬────────┘
                 ↓
        ┌─────────────────┐
        │   Collision     │
        └────────┬────────┘
                 ↓
        ┌─────────────────┐
        │    Animation    │
        └────────┬────────┘
                 ↓
        ┌─────────────────┐
        │     Render      │
        └─────────────────┘
```

---

# 🎯 هدف آموزشی پروژه

این پروژه فقط برای ساخت یک بازی ساده نیست.

هدف اصلی، یادگیری عملی مفاهیم زیر بوده است:

```text
Java
  ↓
OOP
  ↓
Game Loop
  ↓
Input Handling
  ↓
Movement
  ↓
Collision
  ↓
Timer
  ↓
Animation
  ↓
Sprite Sheet
  ↓
Game State
  ↓
Enemy Management
  ↓
Power-Up System
  ↓
Refactoring
  ↓
Resource Management
```

پروژه به صورت مرحله‌به‌مرحله توسعه داده شده تا هر قابلیت جدید همراه با یک مفهوم برنامه‌نویسی یا بازی‌سازی یاد گرفته شود.

---

# 🚀 ایده‌های توسعه آینده

- 🔫 انواع مختلف Bullet
- 🚀 Boost برای سفینه
- 🔥 Animation بهتر برای موتور سفینه
- 👾 Enemyهای جدید
- 👹 Boss Fight
- ❤️ سیستم Health پیشرفته‌تر
- 💥 افکت‌های بیشتر
- 🔊 Sound Effect
- 🎵 موسیقی پس‌زمینه
- 🏆 High Score
- 💾 ذخیره Score
- 🏠 Main Menu
- ⚙️ تنظیمات بازی
- 📱 پشتیبانی از Android

---

# 📝 وضعیت فعلی پروژه

در وضعیت فعلی، بازی دارای هسته اصلی یک Arcade Shooter است:

- حرکت سفینه
- Animation حرکت سفینه
- شلیک
- چند نوع Enemy
- Enemy Spawner
- Difficulty Scaling
- Tank Shooting
- Collision Detection
- Explosion Animation
- Power-Up
- Rapid Fire
- افزایش HP
- Score
- Pause
- Game Over
- Restart
- Background
- مدیریت منابع و Assetها

این پروژه می‌تواند به عنوان پایه‌ای برای توسعه یک بازی کامل‌تر و همچنین تمرین مفاهیم مهم Java و Game Development مورد استفاده قرار گیرد.
