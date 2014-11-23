$(document).ready(function(){
  $('.list-group-item').on('click', function(e) 
    { 
	  $('.list-group-item.active').removeClass('active'); 
	  $(e.target).addClass('active'); 
    })
})