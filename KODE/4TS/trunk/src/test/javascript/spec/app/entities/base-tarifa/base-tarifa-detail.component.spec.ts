/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { BaseTarifaDetailComponent } from 'app/entities/base-tarifa/base-tarifa-detail.component';
import { BaseTarifa } from 'app/shared/model/base-tarifa.model';

describe('Component Tests', () => {
  describe('BaseTarifa Management Detail Component', () => {
    let comp: BaseTarifaDetailComponent;
    let fixture: ComponentFixture<BaseTarifaDetailComponent>;
    const route = ({ data: of({ baseTarifa: new BaseTarifa(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [BaseTarifaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BaseTarifaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BaseTarifaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.baseTarifa).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
