(function($) {
    $('#tiles').imagesLoaded(function() {
        function comparatorName(a, b) {
            return $(a).data('name') < $(b).data('name') ? -1 : 1;
        }
        function comparatorPopularity(a, b) {
            return $(a).data('popularity') > $(b).data('popularity') ? -1 : 1;
        }
        function comparatorTime(a, b) {
            return $(a).data('time') < $(b).data('time') ? -1 : 1;
        }

        // Prepare layout options.
        var options = {
            autoResize: true,
            // This will auto-update the layout when the browser window is resized.
            container: $('#tiles'),
            // Optional, used for some extra CSS styling
            offset: 20,
            // Optional, the distance between grid items
            itemWidth: 300,
            // Optional, the width of a grid item
            comparator: comparatorTime,
            outerOffset: 10,
            // Optional the distance from grid to parent
            flexibleWidth: '50%' // Optional, the maximum width of a grid item
        };

        // Get a reference to your grid items.
        var $handler = $('#tiles li'),
        $sortbys = $('#sortbys li');

        // Call the layout function.
        setTimeout(function(){
            console.log(2)
            $handler.wookmark(options);
        } , 500);

        var $window = $(window);
        $window.resize(function() {
            var windowWidth = $window.width(),
            newOptions = {
                flexibleWidth: '50%'
            };

            // Breakpoint
            if (windowWidth < 1024) {
                newOptions.flexibleWidth = '100%';
            }

            $handler.wookmark(newOptions);
        });

        /**
       * When a filter is clicked, toggle it's active state and refresh.
       */
        var onClickSortBy = function(e) {
            e.preventDefault();
            $currentSortby = $(this);
            switch ($currentSortby.data('sortby')) {
            case 'time':
                options.comparator = comparatorTime;
                break;
            case 'popularity':
                options.comparator = comparatorPopularity;
                break;
            case 'name':
            default:
                options.comparator = comparatorName;
                break;
            }

            $sortbys.removeClass('active');
            $currentSortby.addClass('active');

            $handler.wookmark(options);
        }

        // Capture filter click events.
        $sortbys.click(onClickSortBy);

        // 滚动加载
        //定义滚动函数
        var onScroll = function(event) {
            //是否到底部（这里是判断离底部还有100px开始载入数据）.
            var closeToBottom = ($(window).scrollTop() + $(window).height() > $(document).height() - 100);
            if (closeToBottom) {
                //这里就是AJAX载入的数据               
                $.ajax({
                    url: "data_1.html",
                    dataType: "html",
                    success: function(html) {
                        //把新数据追加到对象中
                        $('#tiles').append(html);
                        //清除原来的定位
                        //if ($handler) $handler.wookmarkClear();
                        //创建新的wookmark对象
                        $handler = $('#tiles li');
                        $handler.wookmark(options);
                    }
                });
            }
        };

        //绑定scroll事件.
        $(document).bind('scroll', onScroll);

    });
})(jQuery);