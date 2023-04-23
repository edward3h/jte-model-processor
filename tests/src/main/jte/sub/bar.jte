@param String name
@param java.time.LocalDate ... dates

<div>Hello, ${name}</div>
@for(var d: dates)
 <div>${d.toString()}</div>
@endfor