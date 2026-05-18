/**
 * Hämta väderdata
 * 
 * 
 */


export type wData = {
    temperaturNow: string
    temperaturNextHour: string
    lat: string
    lon: string
}


/**
 * Hämta data från SMHI
 * Data som hämtas: de två enteriesarna som är närmast i tid. dvs nuvarande temperatur och nästkommande timmes temperatur
 * 
 * @param currency valuta
 */
export async function getWeather(lat: string, lon: string): Promise<wData>{

    const fixedLat = parseFloat(lat).toFixed(4)
    const fixedLon = parseFloat(lon).toFixed(4)

    let url = "https://opendata-download-metfcst.smhi.se/api/category/snow1g/version/1/geotype/point/lon/" + fixedLon + "/lat/" + fixedLat + "/data.json?timeseries=2&parameters=air_temperature"

    const response = await fetch(url)
    const data = await response.json()

    return {   
        temperaturNow : data.timeSeries[0].data.air_temperature,
        temperaturNextHour : data.timeSeries[1].data.air_temperature,
        lat: fixedLat.toString(),
        lon: fixedLon.toString()
    }

}
