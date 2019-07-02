/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { CategoriaSkillDetailComponent } from 'app/entities/categoria-skill/categoria-skill-detail.component';
import { CategoriaSkill } from 'app/shared/model/categoria-skill.model';

describe('Component Tests', () => {
  describe('CategoriaSkill Management Detail Component', () => {
    let comp: CategoriaSkillDetailComponent;
    let fixture: ComponentFixture<CategoriaSkillDetailComponent>;
    const route = ({ data: of({ categoriaSkill: new CategoriaSkill(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [CategoriaSkillDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CategoriaSkillDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategoriaSkillDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categoriaSkill).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
