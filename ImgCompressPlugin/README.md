插件使用算法

pngquant算法压缩png
guetzli算法压缩jpg
cwebp算法转换webp

全量压缩png和jpg图片，每张图能节省百分之70大小
最大化收益下对图片进行webp转换 (after v0.0.3 support)
插件自动化匹配当前操作系统，包括Linux，Mac，Windows (after v0.0.4 support)
插件接入简单，无感知，仅要一行代码

你可以在build.gradle中配置插件的几个属性，如果不设置，所有的属性都使用默认值
McImageConfig {
  isCheckSize true //是否检测图片大小，默认为true
  optimizeType "Compress" //优化类型，可选"ConvertWebp"，"Compress"，转换为webp或原图压缩，默认Compress，使用ConvertWep需要min sdk >= 18.但是压缩效果更好
  maxSize 1*1024*1024 //大图片阈值，default 1MB
  enableWhenDebug false //debug下是否可用，default true
  isCheckPixels true // 是否检测大像素图片，default true
  maxWidth 1000 //default 1000 如果开启图片宽高检查，默认的最大宽度
  maxHeight 1000 //default 1000 如果开启图片宽高检查，默认的最大高度
  whiteList = [ //默认为空，如果添加，对图片不进行任何处理
             "icon_launcher.png"
  ]
  mctoolsDir "$rootDir"
  isSupportAlphaWebp false  //是否支持带有透明度的webp，default false,带有透明图的图片会进行压缩
  multiThread true  //是否开启多线程处理图片，default true 
  bigImageWhiteList = [] //默认为空，如果添加，大图检测将跳过这些图片
}

