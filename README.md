# BereshitP

הקוד:
הקוד שיש לנו מבוסס על קוד שניתן לנו במטלה .
לפי הקוד אם // over 2 km above the ground
if (bereshit.alt > 2000) {
אם החללית היא במרחק של 2KM מעל הירח אזי :
// maintain a vertical speed of [20-25] m/s
if (bereshit.vs > 25) {
   NN += 0.003 * bereshit.dt;
} // more power for braking
if (bereshit.vs < 20) {
   NN -= 0.003 * bereshit.dt;
} // less power for braking
ז"א הבלימה שצריכים לעשות זה כמו שמתואר בקוד 
אבל אם החללית במרחק פחות מ 2KM אזי נגדיר PID שהוא שולט בעצם על הבלימה של החללית ,אם החללית ירדה לגובה מתחת ל 110
if (bereshit.alt < 110) { // close to the ground!
   NN = 1;
   if (bereshit.vs < 5) {
       NN = 0.7;
   }
 
אנחנו מגדילים את הכוח של הבלימה עד לנחיתה.

