# Compose版计算器
> 为了参加Compose 学习挑战赛，前前后后边学边做大约花了两三星期时间。
> 除了基础的计算部分，适配横竖屏，暗黑主题外，这次主要学习的重点放在了手势和动效上面，具体效果如下。

# 效果图

![Screenshot](screenshot/screenshot1.gif)
![Screenshot](screenshot/screenshot2.gif)

# 未解决的问题

* `LazyColumn`水波纹效果无法去除，设置了`indication = null`后，水波纹也依然存在，不知道是不是个bug。
* `LazyColumn`下的item内`horizontalScroll`无效问题，上下滑动的列表中想要加一个左右滑动的item，实验了很久也不行，无奈去除。
* `SwipeToDismiss`怎么设置滑动距离呢？本来想使用`SwipeToDismiss`做一个历史记录横滑删除效果，但是在使用`SwipeToDismiss`设置横滑后，直接就到滑到底了，怎么设置只滑动一段一半或者固定大小的距离呢？
* `SwipeToDismiss`在`if(dismissState.isDismissed(DismissDirection.StartToEnd))`后删除数据源会导致数组越界，但是在item的`clickable`中删除是没问题的，搞了好久没搞定，最终只能遗憾去除。
* `swipeable`和`LazyColumn`的滑动冲突问题，两者始终只能有一个滑动，曾尝试监听`LazyColumn`的滑动事件，根据是否滑动到底部来设置`userScrollEnabled`，但是要么是滑动事件监听无效，要么是设置了也不行，最终也只能放弃。

# 总结
Compose确实可以大大提升开发效率，理解了声明示UI的编码思路后，上手也十分简单，用很少的代码就能实现View体系下复杂的UI效果。  
但是，是的，要说但是了，在这几星期的开发中，遇到了几个让我觉得很痛苦的事情。  
1. API变动导致的学习割裂，去年刚接触Compose的bate版本的时候用到过几个API，但是在1.2.0中要么无了，要么是变样了，就不得不再去翻阅一下官方文档。
更痛苦的是当你在搜索引擎查询该api时，大部分答案居然也都是旧的，看来大家应该都得重新学习了。
2. 学习资料太少，我本人是从compose诞生起就在关注学习了，从一开始的官方文档再到声名鹊起的Compose博物馆，一路学下来后发现他们所列出资料都太简单了，都仅限于了解的程度，如果要想用到一些高级效果，要么就是自己摸索，要么只能去Google，但是更痛苦的事情来了，在我搜索Compose滑动冲突时,全网都找不出几篇文章来。
而这带来的效应就是我实在没勇气将Compose使用到我们的项目中，万一遇到了什么问题无法解决，求助无门的情况下，其所带来的问题成本可能会远比节省下来时间成本大上很多。
3. 库查找困难，举一个例子，在我看了官方文档后也想要咋Compose项目使用ViewModel时，居然发现官方文档没写明还需要引入`lifecycle-viewmodel-compose`库，王德发，类似的栗子比比皆是，除了Compose最主要的五个库之外，其他的官方好像根本不关注，甚至不愿在文档中提一嘴，
而且最痛苦的是，我们完全不知道还有这些库，等Google完看到别人使用才恍然大悟，原来还有这么个好东西。
  
综上所述，我觉得Compose还需要一段时间才能追上view体系所能达到的程度，我坚信compose未来会无比辉煌，但是漫漫长路，前途坎坷，祖君共勉。
