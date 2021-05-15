import { Beer } from './beer';
import { Store } from './store';

export class Offer {
  constructor(
    public store: Store,
    public beer: Beer,
    public price: number,
    public startDate: Date | null,
    public endDate: Date | null
  ) {}
}
