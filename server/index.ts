import express from 'express';
import { NavigationView, BankView, TransferBalanceView, NoDestinationView, AddAccountView, RemoveAccountView, LocationPermissionView, WeatherView, NoAccessWeatherView } from './library/layouts.ts';
import { startDB, transferBalance, deleteAccount, addAccount } from './database/lowdb.ts';
import type {  ServerResponseMessage } from './library/types.ts';
import type { wData } from './weather_api/weather.ts';
import { getWeather } from './weather_api/weather.ts';


const app = express();
app.use(express.json());
const port = 3000;
await startDB()


// ----- STARTSIDA -----
app.get('/start', (req, res) => {
  res.json(NavigationView());
})


// ----- VÄDER -----
app.get('/weather', (req, res) => {
  res.json(LocationPermissionView());
})


app.get('/weather/data', async (req, res) => {
  const { access, lon, lat } = req.query

  if(access == "true"){
    try{
      var data: wData
      if(lon == "-122.0089189" || lon == "-122.08395287867832"){
        data = await getWeather("62.3963", "17.3043");
      }else{
        data = await getWeather(lat as string, lon as string);
      }
      res.json(WeatherView(data));
    } catch (err){
      console.log("Ett fel uppstod: " + err)
      res.json(WeatherView({temperaturNow: "error", temperaturNextHour: "error", lat: "error", lon: "error"}));
    }
  } else {
    res.json(NoAccessWeatherView());
  }

})
  
// ----- BANKEN -----
app.get('/banken', (req, res) => {
  res.json(BankView());
})


// ----- ÖVERFÖRING -----
app.get('/banken/transferBalance', (req, res) => {
  res.json(TransferBalanceView());
})


app.post('/banken/transferBalance', async (req, res) => {
  const { from, to, sum } = req.body

  try{
    const serverResponse: ServerResponseMessage = await transferBalance(from, to, sum);

    res.json({
      title: serverResponse.title,
      message: serverResponse.message
    })
  } catch (err){
    res.json({
      title: "Ett fel uppstod",
      message: "Överföringen misslyckades"
    })
  }

})


// ----- TA BORT KONTO -----
app.get('/banken/Account/Remove', (req, res) => {
  res.json(RemoveAccountView());
})

app.post('/banken/Account/Remove', async (req, res) => {
  const { account } = req.body

  try{
  const serverResponse: ServerResponseMessage = await deleteAccount(account);

  res.json({
    title: serverResponse.title,
    message: serverResponse.message
  })
  } catch (err){
    res.json({
    title: "Ett fel uppstod",
    message: "Borttagningen misslyckades"
  })
  }

})

// ----- LÄGG TILL KONTO -----

app.get('/banken/Account/Add', (req, res) => {
  res.json(AddAccountView());
})


app.post('/banken/Account/Add', async (req, res) => {
  const { currency } = req.body

  try{
  const serverResponse: ServerResponseMessage = await addAccount(currency);

  res.json({
    title: serverResponse.title,
    message: serverResponse.message
  })
  } catch (err){
    res.json({
    title: "Ett fel uppstod",
    message: "Skapandet av konto misslyckades"
  })
  }

})

// ----- ERROR MEDDELANDE -----
app.get('/NoDestination', (req, res) => {
  res.json(NoDestinationView());
})


app.listen(port, '0.0.0.0', () => {
  console.log(`Listening on port ${port} - API is running`)
})
