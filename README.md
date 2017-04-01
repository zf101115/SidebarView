# SidebarView
列表按首字母索引.
核心类：SidebarView.java。
辅助类：ChineseTopinyinyHelper.java。将汉字转换成拼音。

##使用教程
1.在xml布局文件中添加
 '''
  <TextView//此textview为在界面中显示当前按住的字母
        android:id="@+id/textView_dialog"
        android:layout_width="80dp"
        android:layout_height="80dp"
        android:layout_centerInParent="true"
        android:background="@drawable/voip_toast_bg"
        android:gravity="center"
        android:textColor="#fff"
        android:textSize="30sp"
        android:visibility="invisible" />

<com.steven.contactssortletter.SidebarView
        android:id="@+id/sidebarView_main"
        android:layout_width="30dp"
        android:layout_height="match_parent"
        android:layout_alignParentRight="true" />
'''
2.在adapter中实现SectionIndexer接口
'''
public class myAdapter extends BaseAdapter implements SectionIndexer{
    @Override
    public Object[] getSections() {
        return null;
    }

    // 做字母索引的时候常常会用到SectionIndexer这个接口
    // 1. getSectionForPosition() 通过该项的位置，获得所在分类组的索引号
    // 2. getPositionForSection() 根据分类列的索引号获得该序列的首个位置

    @Override
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String firstLetter = list.get(i).getFirstLetter();
            char firstChar = firstLetter.charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    // 根据position获取分类的首字母的Char ascii值
    @Override
    public int getSectionForPosition(int position) {
        return list.get(position).getFirstLetter().charAt(0);
    }
}
'''       
3.在activity中获取list的每一项的拼音首字母
'''
    sidebarView_main.setTextView(textView_dialog);//传入显示当前字母的view

    for (int i = 0; i < arrUsers.length; i++) {
            Area area = new Area();
            String username = arrUsers[i];
            String pinyin = ChineseToPinyinHelper.getInstance().getPinyin(
                    username);
            String firstLetter = pinyin.substring(0, 1).toUpperCase();
            if (firstLetter.matches("[A-Z]")) {
                area.setFirstLetter(firstLetter);
            } else {
                area.setFirstLetter("#");
            }
            area.setUesrname(username);
            totallList.add(area);
        }
        sidebarView_main
                .setOnLetterClickedListener(new OnLetterClickedListener() {
                    @Override
                    public void onLetterClicked(String str) {
                        int position = adapter.getPositionForSection(str
                                .charAt(0));
                        listView_users.setSelection(position);
                    }
                });

'''
#效果图
![image](https://github.com/zf101115/SidebarView/raw/master/screenshort/34834063844878021.png)