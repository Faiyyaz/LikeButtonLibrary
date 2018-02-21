# LikeButton

Thanks to ChadCSong

LikeButton has been created by using ShineButton relased by ChadCSong but it has a little change which i was required for my project i.e. suppose if you are calling an API for favorite or unfavorite & you want to start stop animation after response then it was not possible in ShineButton. But it had been made possible in LikeButton by using the `setcheked()` method of likebutton.

LikeButton can be used in your project by using the following implementation statement

```implementation 'com.dexter.likebutton:like:1.0'```

## Usage
```java
 mLikeButton= (LikeButton) findViewById(R.id.shine_button);
 mLikeButton.init(activity);
```
or

```java
 LikeButton mLikeButton= new LikeButton(this);
 mLikeButton.setBtnColor(Color.GRAY);
 mLikeButton.setBtnFillColor(Color.RED);
 mLikeButton.setShapeResource(R.raw.heart);
 mLikeButton.setAllowRandomColor(true);
 LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 100);
 mLikeButton.setLayoutParams(layoutParams);
 if (linearLayout != null) {
     linearLayout.addView(mLikeButton);
 }
```
#### Simple Usage

Icon shape is made from png mask. Please see raw files.
```shell
app:shape
```

Default button color.
```shell
app:btn_color
```
Fill button color.
```shell
app:btn_fill_color
```
If this property is true,the effects will become random color shine.
```shell
app:allow_random_color
```

```xml
 <com.dexter.likebuttonlib.LikeButton
                android:layout_width="50dp"
                android:layout_height="50dp"
                android:layout_centerInParent="true"
                android:src="@android:color/darker_gray"
                android:id="@+id/po_image2"
                app:btn_color="@android:color/darker_gray"
                app:btn_fill_color="@android:color/holo_green_dark"
                app:allow_random_color="false"
                app:shape="@raw/smile"/>
 ```

 ```xml
 <com.dexter.likebuttonlib.LikeButton
                 android:layout_width="50dp"
                 android:layout_height="50dp"
                 android:layout_centerInParent="true"
                 android:src="@android:color/darker_gray"
                 android:id="@+id/po_image1"
                 app:btn_color="@android:color/darker_gray"
                 app:btn_fill_color="#FF6666"
                 app:allow_random_color="false"
                 app:enable_flashing="false"
                 app:big_like_color="#FF6666"
                 app:click_animation_duration="200"
                 app:like_animation_duration="1500"
                 app:like_turn_angle="10"
                 app:small_like_offset_angle="20"
                 app:like_distance_multiple="1.5f"
                 app:small_like_color="#CC9999"
                 app:like_count="8"
                 app:shape="@raw/like"/>
  ```