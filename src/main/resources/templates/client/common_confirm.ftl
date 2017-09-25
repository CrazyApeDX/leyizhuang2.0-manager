	<link rel="stylesheet" type="text/css" href="/client/css/my_base.css"/></link>
	<style>
            .win_yn{
                position: fixed;
                left: 0px;
                top: 0px;
                width: 100%;
                height: 100%;
                z-index:1000
            }
            .win_yn div{
                width:220px;
                height: 120px;
                margin: auto;
                border-radius: 8px;
                background: rgba(0,0,0,0.5);
            }
            .win_yn div p{
                float: left;
                width: 100%;
                margin-top: 20px;
                line-height: 30px;
                font-size: 1.2em;
                color: white;
                text-align: center;
            }
            .win_yn div span{
                float: left;
                width: 72%;
                padding: 0 14%;
            }
            .win_yn div span label{
                float: left;
                width: 60px;
                height: 28px;
                margin-top: 20px;
                line-height: 28px;
                color: white;
                text-align: center;
                border: white 1px solid;
                border-radius: 6px;
            }
            .win_yn div span label:nth-of-type(1){
                float: left;
            }
            .win_yn div span label:nth-of-type(2){
                float: right;
            }
        </style>
	</style>
	<script type="text/javascript">
		function win_yes(text,func){
			$("#title_by_dx").html(text);
			var he = ($(window).height() - $('.win_yn div').height())/2 - 50;
			$('.win_yn div').css({marginTop:he});	
			$('.win_yn').fadeIn(600);
			$("#yes").attr("onclick",func);
		};
		
		function win_no(){	
			$('.win_yn').fadeOut(600);
		};	
	</script>
	<div class="win_yn" style="display:none;">
		<div>					
			<p id="title_by_dx"></p>
			<span>
				<label id="yes">是</label>
				<label onclick="win_no();">否</label>
			</span>				
		</div>
	</div>
